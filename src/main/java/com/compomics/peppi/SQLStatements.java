package com.compomics.peppi;

/**
 *
 * @author Davy
 */
public class SQLStatements {

    public static final String CHECKIFCOLIMS = "show tables like 'analytical_run'";
    public static final String CHECKIFELIENDB = "show tables like 'pdb'";
    private static String SELECT_PROTEINS;
    private static String SELECT_PEPTIDEGROUPS;
    private static String SELECT_PROJECTS;
    private static String SELECT_A_SINGLE_PROJECT;
    private static String SELECT_PEPTIDEGROUPS_FOR_ACCESSION;
    private static String SELECT_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION;
    private static String CHECK_IF_PROJECT_HAS_QUANTDATA;
    private static String GET_PDBFILES_FOR_PROTEIN;

    public static void instantiateColimsStatements() {
        SQLStatements.SELECT_PROTEINS = SELECT_COLIMS_PROTEINS;
        SQLStatements.SELECT_PROJECTS = SELECT_COLIMS_PROJECTS;
        SQLStatements.SELECT_PEPTIDEGROUPS = SELECT_COLIMS_PEPTIDEGROUPS;
        SQLStatements.SELECT_A_SINGLE_PROJECT = SELECT_COLIMS_SINGLE_PROJECT;
        SQLStatements.SELECT_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION = SELECT_COLIMS_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION;
        SQLStatements.SELECT_PEPTIDEGROUPS_FOR_ACCESSION = SELECT_COLIMS_PEPTIDEGROUPS_FOR_ACCESSION;
        SQLStatements.CHECK_IF_PROJECT_HAS_QUANTDATA = CHECK_IF_COLIMS_PROJECT_HAS_QUANTDATA;
        SQLStatements.GET_PDBFILES_FOR_PROTEIN = GET_PDBFILES_FOR_PROTEIN_FROM_COLIMS;
    }

    public static void instantiateMslimsStatements() {
        SQLStatements.SELECT_PROTEINS = SELECT_MSLIMS_PROTEINS;
        SQLStatements.SELECT_PROJECTS = SELECT_MSLIMS_PROJECTS;
        SQLStatements.SELECT_PEPTIDEGROUPS = SELECT_MSLIMS_PEPTIDEGROUPS;
        SQLStatements.SELECT_A_SINGLE_PROJECT = SELECT_MSLIMS_SINGLE_PROJECT;
        SQLStatements.SELECT_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION = SELECT_MSLIMS_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION;
        SQLStatements.SELECT_PEPTIDEGROUPS_FOR_ACCESSION = SELECT_MSLIMS_PEPTIDEGROUPS_FOR_ACCESSION;
        SQLStatements.CHECK_IF_PROJECT_HAS_QUANTDATA = CHECK_IF_MSLIMS_PROJECT_HAS_QUANTDATA;
        SQLStatements.GET_PDBFILES_FOR_PROTEIN = GET_PDBFILES_FOR_PROTEIN_FROM_MSLIMS;
    }

    //TODO rename
    public static void instantiateElienDbStatements() {
        SQLStatements.SELECT_PROTEINS = SELECT_ELIEN_PROTEINS;
        SQLStatements.SELECT_PROJECTS = SELECT_ELIEN_PROJECTS;
        SQLStatements.SELECT_PEPTIDEGROUPS = SELECT_ELIEN_PEPTIDEGROUPS;
        SQLStatements.SELECT_A_SINGLE_PROJECT = SELECT_ELIEN_SINGLE_PROJECT;
        SQLStatements.SELECT_PEPTIDEGROUPS_FOR_ACCESSION = SELECT_ELIEN_PEPTIDEGROUPS_FOR_ACCESSION;
        SQLStatements.CHECK_IF_PROJECT_HAS_QUANTDATA = CHECK_IF_ELIENDB_PROJECT_HAS_QUANTDATA;
        SQLStatements.GET_PDBFILES_FOR_PROTEIN = GET_PDBfILES_FOR_PROTEIN_FROM_ELIENDB;
    }
    private static String SELECT_MSLIMS_PROTEINS = "select distinct accession from identification,spectrum where l_spectrumid = spectrumid and l_projectid = ? order by accession";
    private static String SELECT_COLIMS_PROTEINS = "";
    private static String SELECT_ELIEN_PROTEINS;
    private static String SELECT_MSLIMS_PEPTIDEGROUPS = "select identification.sequence from identification,spectrum where l_spectrumid = spectrumid and l_projectid = ? and identification.accession = ?";
    private static String SELECT_COLIMS_PEPTIDEGROUPS = "";
    private static String SELECT_ELIEN_PEPTIDEGROUPS;
    private static String SELECT_MSLIMS_PROJECTS = "select projectid,title from project order by projectid";
    private static String SELECT_COLIMS_PROJECTS = "";
    private static String SELECT_ELIEN_PROJECTS;
    private static String SELECT_MSLIMS_SINGLE_PROJECT = "select title from project where projectid = ? order by projectid";
    private static String SELECT_COLIMS_SINGLE_PROJECT = "";
    private static String SELECT_ELIEN_SINGLE_PROJECT;
    private static String SELECT_MSLIMS_PEPTIDEGROUPS_FOR_ACCESSION = "select sequence from identification,spectrum where l_spectrumid = spectrumid and l_projectid = ? and identification.accession = ?";
    private static String SELECT_COLIMS_PEPTIDEGROUPS_FOR_ACCESSION = "";
    private static String SELECT_ELIEN_PEPTIDEGROUPS_FOR_ACCESSION;
    private static String SELECT_MSLIMS_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION = "select l_quantitation_groupid,ratio,result.sequence,type from identification_to_quantitation,quantitation,(select identificationid as id,sequence from identification,spectrum where l_spectrumid = spectrumid and l_projectid = ? and identification.accession = ?) as result where result.id = identification_to_quantitation.l_identificationid and identification_to_quantitation.l_quantitation_groupid = quantitation.l_quantitation_groupid";
    private static String SELECT_COLIMS_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION = "";
    private static String CHECK_IF_MSLIMS_PROJECT_HAS_QUANTDATA = "select l_identificationid from identification_to_quantitation,(select identificationid as ident_id from identification,(select spectrumid as spec_id from spectrum where l_projectid = ? limit 1)as spectrum_result where l_spectrumid = spectrum_result.spec_id limit 1) as ident_result where ident_result.ident_id = l_identificationid limit 1";
    private static String CHECK_IF_COLIMS_PROJECT_HAS_QUANTDATA;
    private static String CHECK_IF_ELIENDB_PROJECT_HAS_QUANTDATA = "select * from somwhere where column = null";
    private static String GET_PDBFILES_FOR_PROTEIN_FROM_MSLIMS = "";
    private static String GET_PDBFILES_FOR_PROTEIN_FROM_COLIMS = "";
    private static String GET_PDBfILES_FOR_PROTEIN_FROM_ELIENDB = "";

    public static String selectAllProteins() {
        return SELECT_PROTEINS;
    }

    public static String selectAllPeptidesGrouped() {
        return SELECT_PEPTIDEGROUPS;
    }

    public static String selectAllProjects() {
        return SELECT_PROJECTS;
    }

    public static String selectASingleProject() {
        return SELECT_A_SINGLE_PROJECT;
    }

    public static String selectAllPeptidesGroupedForProteinAccession() {
        return SELECT_PEPTIDEGROUPS_FOR_ACCESSION;
    }

    public static String selectAllQuantedPeptideGroups() {
        return SELECT_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION;
    }

    public static String QuantedCheck() {
        return CHECK_IF_PROJECT_HAS_QUANTDATA;
    }

    public static String getPdbFilesFromDb() {
        return GET_PDBFILES_FOR_PROTEIN;
    }
}
