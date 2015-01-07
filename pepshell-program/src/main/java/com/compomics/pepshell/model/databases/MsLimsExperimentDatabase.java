/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package com.compomics.pepshell.model.databases;

/**
 *
 * @author Davy Maddelein
 */
public class MsLimsExperimentDatabase extends ExperimentDatabase {

    @Override
    public String getQuantForExperiment() {
        return "steal this";
    }

    @Override
    public String getPeptidesWithQuant() {
        return "steal this";
    }

    @Override
    public String selectAllProteins() {
        return "select distinct accession from identification,spectrum where l_spectrumid = spectrumid and l_projectid = ? order by accession";

    }

    @Override
    public String selectAllPeptidesGrouped() {
        return "select identification.identificationid,identification.sequence,identification.start,identification.end,spectrum.total_spectrum_intensity from identification,spectrum where l_spectrumid = spectrumid and l_projectid = ? and identification.accession = ?";
    }

    @Override
    public String selectAllExperiments() {
        return "select projectid,title from project order by projectid";

    }

    @Override
    public String selectASingleExperiment() {
        return "select title from project where projectid = ? order by projectid";
    }

    @Override
    public String selectAllPeptidesGroupedForProteinAccession() {
        return "select sequence from identification,spectrum where l_spectrumid = spectrumid and l_projectid = ? and identification.accession = ?";
    }

    @Override
    public String selectAllQuantedPeptideGroups() {
        return "select identification_to_quantitation.l_quantitation_groupid,ratio,result.sequence,identification_to_quantitation.type,result.id,quantitation.standard_error from identification_to_quantitation,quantitation,(select identificationid as id,sequence from identification,spectrum where l_spectrumid = spectrumid and l_projectid = ? and identification.accession = ?) as result where result.id = identification_to_quantitation.l_identificationid and identification_to_quantitation.l_quantitation_groupid = quantitation.l_quantitation_groupid";
    }

    @Override
    public String ExperimentHasQuant() {
        return "select l_identificationid from identification_to_quantitation,(select identificationid as ident_id from identification,(select spectrumid as spec_id from spectrum where l_projectid = ? limit 1)as spectrum_result where l_spectrumid = spectrum_result.spec_id limit 1) as ident_result where ident_result.ident_id = l_identificationid limit 1";
    }

    @Override
    public String getErrorForQuantedPeptide() {
        return "select quantitation.standard_error from quantitation, identification_to_quantitation where quantitation.l_quantitation_groupid = identification_to_quantitation.l_quantitation_groupid and l_identificationid = ?";
    }

    @Override
    public String getQuantForPeptide() {
        return "select quantitation.ratio from quantitation, identification_to_quantitation where quantitation.l_quantitation_groupid = identification_to_quantitation.l_quantitation_groupid and l_identificationid = ?";

    }

}
