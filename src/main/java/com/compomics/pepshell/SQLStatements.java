package com.compomics.pepshell;

/**
 *
 * @author Davy
 */
public class SQLStatements {

    public static final String CHECKIFCOLIMS = "show tables like 'analytical_run'";
    public static final String CHECKIFLINKDB = "show tables like 'pdb'";
    private static String SELECT_PROTEINS;
    private static String SELECT_PEPTIDEGROUPS;
    private static String SELECT_PROJECTS;
    private static String SELECT_A_SINGLE_PROJECT;
    private static String SELECT_PEPTIDEGROUPS_FOR_ACCESSION;
    private static String SELECT_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION;
    private static String CHECK_IF_PROJECT_HAS_QUANTDATA;
    private static String GET_PDBFILES_FOR_PROTEIN;
    private static String GET_INTERACTIONPARTNERS_FOR_RESIDUE;
    private static String GET_PDB_DATA_FROM_DB;
    private static String GET_FREE_ENERGY_FOR_RESIDUE;
    private static String GET_RELATIVE_SOLVENT_ACCESSIBILITY_FOR_RESIDUE;
    private static String GET_INTERACTIONPARTNERS_FOR_RANGE;
    private static String GET_QUANT_FOR_EXPERIMENT;
    private static String GET_PEPTIDES_WITH_QUANT;
    private static String GET_INTERACTIONPARTNERS_FOR_PDB;
    private static String GET_PDB_WITH_HIGHEST_RESOLUTION_FOR_PROTEIN;
    private static String GET_PDB_INFO_FOR_PROTEIN;

    public static void instantiateColimsStatements() {
        SQLStatements.SELECT_PROTEINS = SELECT_COLIMS_PROTEINS;
        SQLStatements.SELECT_PROJECTS = SELECT_COLIMS_PROJECTS;
        SQLStatements.SELECT_PEPTIDEGROUPS = SELECT_COLIMS_PEPTIDEGROUPS;
        SQLStatements.SELECT_A_SINGLE_PROJECT = SELECT_COLIMS_SINGLE_PROJECT;
        SQLStatements.SELECT_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION = SELECT_COLIMS_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION;
        SQLStatements.SELECT_PEPTIDEGROUPS_FOR_ACCESSION = SELECT_COLIMS_PEPTIDEGROUPS_FOR_ACCESSION;
        SQLStatements.CHECK_IF_PROJECT_HAS_QUANTDATA = CHECK_IF_COLIMS_PROJECT_HAS_QUANTDATA;
    }

    public static void instantiateMslimsStatements() {
        SQLStatements.SELECT_PROTEINS = SELECT_MSLIMS_PROTEINS;
        SQLStatements.SELECT_PROJECTS = SELECT_MSLIMS_PROJECTS;
        SQLStatements.SELECT_PEPTIDEGROUPS = SELECT_MSLIMS_PEPTIDEGROUPS;
        SQLStatements.SELECT_A_SINGLE_PROJECT = SELECT_MSLIMS_SINGLE_PROJECT;
        SQLStatements.SELECT_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION = SELECT_MSLIMS_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION;
        SQLStatements.SELECT_PEPTIDEGROUPS_FOR_ACCESSION = SELECT_MSLIMS_PEPTIDEGROUPS_FOR_ACCESSION;
        SQLStatements.CHECK_IF_PROJECT_HAS_QUANTDATA = CHECK_IF_MSLIMS_PROJECT_HAS_QUANTDATA;
        SQLStatements.GET_QUANT_FOR_EXPERIMENT = SELECT_QUANT_DATA_FOR_MS_LIMS_PROJECT;
        SQLStatements.GET_PEPTIDES_WITH_QUANT = SELECT_PEPTIDES_WITH_QUANT_FOR_MS_LIMS_PROJECT;
    }

    public static void instantiateLinkDbStatements() {
        SQLStatements.GET_PDBFILES_FOR_PROTEIN = GET_PDBFILES_FOR_PROTEIN_FROM_LINKDB;
        SQLStatements.GET_INTERACTIONPARTNERS_FOR_RESIDUE = GET_INTERACTIONPARTNERS_FOR_RESIDUE_FROM_LINKDB;
        SQLStatements.GET_INTERACTIONPARTNERS_FOR_RANGE = GET_INTERACTIONPARTNERS_FOR_RANGE_FROM_LINKDB;
        SQLStatements.GET_PDB_DATA_FROM_DB = GET_PDB_FILES_FROM_LINKDB;
        SQLStatements.GET_FREE_ENERGY_FOR_RESIDUE = GET_FREE_ENERGY_FOR_RESIDUE_FROM_LINKDB;
        SQLStatements.GET_RELATIVE_SOLVENT_ACCESSIBILITY_FOR_RESIDUE = GET_RELATIVE_SOLVENT_ACCESSIBILITY_FOR_RESIDUE_FROM_LINKDB;
        SQLStatements.GET_INTERACTIONPARTNERS_FOR_PDB = GET_INTERACTION_PARTNERS_FOR_PDB_FROM_LINKDB;
        SQLStatements.GET_PDB_WITH_HIGHEST_RESOLUTION_FOR_PROTEIN = GET_PDBFILE_WITH_HIGHEST_RESOLUTION_FOR_PROTEIN_FROM_LINKDB;
        SQLStatements.GET_PDB_INFO_FOR_PROTEIN = GET_PDBINFO_FOR_PROTEIN_FROM_LINKDB;
        
    }

    //TODO move these to enums, split up peptide/protein queries(ms-colims) and structural db queries (link db)
    private static final String SELECT_MSLIMS_PROTEINS = "select distinct accession from identification,spectrum where l_spectrumid = spectrumid and l_projectid = ? order by accession";
    private static final String SELECT_COLIMS_PROTEINS = "";
    private static final String SELECT_LINKDB_PROTEINS = "select * from protein";
    private static final String SELECT_MSLIMS_PEPTIDEGROUPS = "select identification.sequence,identification.start,identification.end from identification,spectrum where l_spectrumid = spectrumid and l_projectid = ? and identification.accession = ?";
    private static final String SELECT_COLIMS_PEPTIDEGROUPS = "";
    private static final String SELECT_MSLIMS_PROJECTS = "select projectid,title from project order by projectid";
    private static final String SELECT_COLIMS_PROJECTS = "something something experiment";
    private static final String SELECT_MSLIMS_SINGLE_PROJECT = "select title from project where projectid = ? order by projectid";
    private static final String SELECT_COLIMS_SINGLE_PROJECT = "";
    private static final String SELECT_MSLIMS_PEPTIDEGROUPS_FOR_ACCESSION = "select sequence from identification,spectrum where l_spectrumid = spectrumid and l_projectid = ? and identification.accession = ?";
    private static final String SELECT_COLIMS_PEPTIDEGROUPS_FOR_ACCESSION = "";
    private static final String SELECT_MSLIMS_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION = "select l_quantitation_groupid,ratio,result.sequence,type,result.id from identification_to_quantitation,quantitation,(select identificationid as id,sequence from identification,spectrum where l_spectrumid = spectrumid and l_projectid = ? and identification.accession = ?) as result where result.id = identification_to_quantitation.l_identificationid and identification_to_quantitation.l_quantitation_groupid = quantitation.l_quantitation_groupid";
    private static final String SELECT_COLIMS_PEPTIDEGROUPS_WITH_QUANTITATION_FOR_ACCESSION = "";
    private static final String CHECK_IF_MSLIMS_PROJECT_HAS_QUANTDATA = "select l_identificationid from identification_to_quantitation,(select identificationid as ident_id from identification,(select spectrumid as spec_id from spectrum where l_projectid = ? limit 1)as spectrum_result where l_spectrumid = spectrum_result.spec_id limit 1) as ident_result where ident_result.ident_id = l_identificationid limit 1";
    private static final String CHECK_IF_COLIMS_PROJECT_HAS_QUANTDATA = "";
    private static final String GET_PDBFILES_FOR_PROTEIN_FROM_LINKDB = "select PDB from structure";
    private static final String GET_PDBINFO_FOR_PROTEIN_FROM_LINKDB = "select * from structure";
    private static final String GET_INTERACTIONPARTNERS_FOR_RESIDUE_FROM_LINKDB = "select interaction.* from interaction,residue,protein_chain,protein where protein_chain.chain_id = residue.chain_id and protein.idprotein = protein_id and residue_id_partner1 = residue.idresidue and protein.uniprot_id = ?";
    private static final String GET_PDB_FILES_FROM_LINKDB = "select * from structure where PDB = ?";
    private static final String GET_FREE_ENERGY_FOR_RESIDUE_FROM_LINKDB = "select residue.E_total from residue,protein_chain,chain,protein,structure where protein_chain.chain_id = residue.chain_id and protein.idprotein = protein_id and protein.uniprot_id = ? and chain.structure_id = structure.idstructure and chain.idchain = protein_chain.chain_id and structure.PDB = ?";
    private static final String GET_RELATIVE_SOLVENT_ACCESSIBILITY_FOR_RESIDUE_FROM_LINKDB = "select residue.SASrel from residue,protein_chain,protein,chain, (select idstructure from structure order by idstructure desc limit 1)as highest_resolution where protein_chain.chain_id = residue.chain_id and protein.idprotein = protein_id and protein.uniprot_id = ? and chain.structure_id = highest_resolution.idstructure and chain.idchain = protein_chain.chain_id and residue.numbering_fasta = ?";
    private static final String GET_INTERACTIONPARTNERS_FOR_RANGE_FROM_LINKDB = "select interaction.* from interaction,residue,protein_chain,protein where protein_chain.idchain = residue.chain_id and protein.idprotein = protein_id and residue_id_partner1 = residue.idresidue or residue_id_partner2 = residue.idresidue and numbering_fasta between(?,?) and protein.uniprot_id = ?";
    private static final String SELECT_QUANT_DATA_FOR_MS_LIMS_PROJECT = "steal this";
    private static final String SELECT_PEPTIDES_WITH_QUANT_FOR_MS_LIMS_PROJECT = "and steal this";
    private static final String GET_INTERACTION_PARTNERS_FOR_PDB_FROM_LINKDB = "select interaction.* from interaction,residue,chain,structure where chain.idchain = residue.chain_id and chain.structure_id = structure.idstructure and residue_id_partner2 = residue.idresidue and structure.PDB = ?";
    private static final String GET_PDBFILE_WITH_HIGHEST_RESOLUTION_FOR_PROTEIN_FROM_LINKDB = "select PDB from structure order by resolution desc limit 1";
    
    
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

    public static String getInteractionPartnersForResidue() {
        return GET_INTERACTIONPARTNERS_FOR_RESIDUE;
    }

    public static String getPdbDataFromDb() {
        return GET_PDB_DATA_FROM_DB;
    }

    public static String GetFreeEnergyForResidue() {
        return GET_FREE_ENERGY_FOR_RESIDUE;
    }

    public static String getRelativeSolventAccessibilityForResidue() {
        return GET_RELATIVE_SOLVENT_ACCESSIBILITY_FOR_RESIDUE;
    }

    public static String getInteractionPartnersForRange() {
        return GET_INTERACTIONPARTNERS_FOR_RANGE;
    }

    public static String getQuantForExperiment() {
        return GET_QUANT_FOR_EXPERIMENT;
    }

    public static String getPeptidesWithQuant() {
        return GET_PEPTIDES_WITH_QUANT;
    }

    public static String getInteractionPartnersForPDB() {
        return GET_INTERACTIONPARTNERS_FOR_PDB;
    }

    public static String getPdbWithHighestResolutionForProtein(){
        return GET_PDB_WITH_HIGHEST_RESOLUTION_FOR_PROTEIN;
    }
    
    public static String getPdbInfoForProtein(){
        return GET_PDB_INFO_FOR_PROTEIN;
    }
}
