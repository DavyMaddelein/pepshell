package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.DataModeController;
import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.DAO.DbDAO;
import com.compomics.pepshell.controllers.DAO.FastaDAO;
import com.compomics.pepshell.controllers.DAO.PDBDAO;
import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.controllers.DataSources.StructureDataSource;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Davy
 * @param <T>
 * @param <V>
 */
public class ViewPreparationForFastaData<T extends Experiment, V extends Protein> extends ViewPreparation<T, V> {

    //TODO: this entire thing needs cleaning up
    private File fastaFile;

    public void setFastaFile(File aFastaFile) {
        fastaFile = aFastaFile;
    }

    @Override
    public T retrieveData(T referenceExperiment, Iterator<T> experimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        try {
            if (DataModeController.getDataSource() == DataModeController.DataSource.DATABASE) {
                DbDAO.fetchProteins(referenceExperiment);
                if (hasToTranslateAccessions) {
                    //todo multithread
                    for (Protein aProtein : referenceExperiment.getProteins()) {
                        try {
                            aProtein.setAccession(AccessionConverter.ToUniprot(aProtein.getOriginalAccession()));
                        } catch (ConversionException ex) {
                            FaultBarrier.getInstance().handleException(ex);
                        }
                    }
                }
                if (hasToFilter) {
                    referenceExperiment.setProteins(filter.filter(referenceExperiment.getProteins(), filterList));
                }
                if (hasToMask) {
                    for (Protein maskingProtein : maskingSet) {
                        if (referenceExperiment.getProteins().contains(maskingProtein)) {
                            referenceExperiment.getProteins().get(referenceExperiment.getProteins().indexOf(maskingProtein)).setVisibleAccession(maskingProtein.getVisibleAccession());
                        }
                    }
                }
                DbDAO.addPeptideGroupsToProteins(referenceExperiment.getProteins());
                FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, referenceExperiment.getProteins());
                if (hasToAddQuantData) {
                    checkAndAddQuantToProteinsInExperiment(referenceExperiment);
                }
            } else {
                FastaDAO.setProjectProteinsToFastaFileProteins(fastaFile, referenceExperiment);
            }
            if (DataModeController.getDataSource() == DataModeController.DataSource.DATABASE) {
                while (experimentsToCompareWith.hasNext()) {
                    T anExperimentToCompareWith = experimentsToCompareWith.next();
                    DbDAO.fetchPeptidesAndProteins(anExperimentToCompareWith);
                    FastaDAO.mapFastaSequencesToProteinAccessions(fastaFile, anExperimentToCompareWith.getProteins());
                    if (hasToAddQuantData) {
                        checkAndAddQuantToProteinsInExperiment(anExperimentToCompareWith);
                    }
                }
            } else {
                while (experimentsToCompareWith.hasNext()) {
                    //something something CP-DT files
                    T aProjectToCompareWith = experimentsToCompareWith.next();
                }
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            retrieveSecondaryData(referenceExperiment);
        } catch (FastaCouldNotBeReadException ex) {
            FaultBarrier.getInstance().handleException(ex);
        } catch (FileNotFoundException ex) {
            FaultBarrier.getInstance().handleException(ex);
        } catch (IOException ex) {
            FaultBarrier.getInstance().handleException(ex);
        } catch (SQLException ex) {
            FaultBarrier.getInstance().handleException(ex);
        }
        return referenceExperiment;
    }

    @Override
    protected boolean checkAndAddQuantToProteinsInExperiment(T anExperiment) {
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllQuantedPeptideGroups());
            for (Protein protein : anExperiment.getProteins()) {
                stat.setInt(1, anExperiment.getExperimentId());
                stat.setString(1, protein.getProteinAccession());
                ResultSet rs = stat.executeQuery();
                try {
                    while (rs.next()) {

                    }
                } catch (SQLException sqle) {
                }
            }
        } catch (SQLException sqle) {

        }
        return true;
    }

    @Override
    public void addProteinsToExperiment(AbstractDataMode dataMode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void retrieveSecondaryData(T experiment) {
        if (hasToFetchDomainData) {
            for (final List<Protein> partitionedExperimentProteins : Lists.partition(experiment.getProteins(), Runtime.getRuntime().availableProcessors())) {
                new Runnable() {
                    public void run() {
                        StructureDataSource domainDataFetcher = ProgramVariables.STRUCTUREDATASOURCE.getInstance();
                        for (Protein anExperimentProtein : partitionedExperimentProteins) {
                            try {
                                domainDataFetcher.getDomainData(anExperimentProtein);
                            } catch (DataRetrievalException ex) {
                                FaultBarrier.getInstance().handleException(ex);
                            }
                        }
                    }

                };
            }
        }
        if (hasToRetrievePDBData) {
            for (Protein aProtein : experiment.getProteins()) {
                try {
                    PDBDAO.getPDBFileAccessionsForProtein(aProtein);
                } catch (IOException ex) {
                    FaultBarrier.getInstance().handleException(ex);
                } catch (ConversionException ex) {
                    FaultBarrier.getInstance().handleException(ex);
                }
            }
        }
    }
}
