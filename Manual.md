# Table of contents #



---

# PepShell description, requirements, and first start-up #

Proteins are dynamic molecules; they undergo conformational changes induced by post-translational modifications and by binding of cofactors or other molecules. The characterization of these conformational changes and their relation to protein function is a central goal of structural biology. Unfortunately, most conventional methods to obtain structural information do not provide information on protein dynamics. Therefore, mass spectrometry-based approaches such as limited proteolysis, hydrogen-deuterium exchange and stable-isotope labelling are nowadays frequently used to characterize protein conformation and dynamics. Here, we present PepShell, a tool that allows interactive data analysis of mass spectrometry-based conformational proteomics studies by visualization of the identified peptides both at the sequence and at the structure level. Moreover, PepShell allows the comparison of experiments under different conditions including different proteolysis times or binding of the protein to different substrates or inhibitors

## Requirements ##

  * PepShell requires java 1.7 or higher (tested to be working on Oracle and openJDK JVMs).
  * PepShell requires a Window Manager on Linux (tested on Nautilus, KDE, Gnome).

Other than these basic requirements, PepShell is system independent.

## System settings ##

  * We recommend starting at 64 megabytes of RAM (this can be set to a different number in the PepShell configuration depending on the size of the experiment and the requested descriptive data).
  * As PepShell creates temporary files during an experiment comparison, it is not recommended to run PepShell on a full disk.

To enable most of the advanced features, an internet connection is required. However this is optional and all data can be delivered by offline resources as well.

## Downloading and starting up ##

The stable version can always be found on the home page of the PepShell google code site by clicking the download button.

On start-up, if PepShell detects that the current operating system is a Windows machine, it will try to install the C++ redistributable required to run CP-DT (Fannes et al. 2013) automatically and then move on to start normally. You can opt out from this simple installation, and it can then either be switched off entirely. The download can be enabled again at a later point if desired.

On start-up, PepShell will also try to check if a new version is available and will ask you if you want to update. It is possible to skip this step, and even to turn this automatic update check feature off.

## Start-up screen ##

![http://wiki.pepshell.googlecode.com/git/login_1.png](http://wiki.pepshell.googlecode.com/git/login_1.png)

The start-up screen allows you to choose between two data import options:

  * from a database
  * from one or multiple files

You can also select some options that affect the start-up of PepShell, for example the memory consumption and whether the presence of CP-DT libraries install should be checked for at start-up.

[Go to top of page](#Table_of_contents.md)

---

# Loading data from a local data file #

## Supported formats and handling unsupported formats ##

Supported formats are identified by their extension. PepShell currently supports HDXchange formatted files (`.hdx`) in this way. Unknown extensions will not be recognized by default, and cannot be loaded automatically.

In order to load such unsupported format files, it is sufficient if you provide metadata about your file(s) so PepShell can read these files. More details about adding metadata can be found [here](#Adding_metadata_about_files.md). Do note that while this approach for reading data files offers considerable flexibility regarding the formatting, highly heterogeneous data will most likely not be parseable. PepShell will currently read only text files, so spreadsheet files should first be _saved as_ or exported in a tab-delimited or similarly formatted text file.

Do note that an [example data file](http://genesis.ugent.be/pepshell/testfiles/) is provided (see [further](#An_example.md)).

## Loading multiple experiments from multiple files ##

A series of experiments or experimental repeats that have been split up in separate files can be added separately or in batch and will be considered as separate experiments as far as further processing in PepShell is concerned.

![http://wiki.pepshell.googlecode.com/git/select_files_1.png](http://wiki.pepshell.googlecode.com/git/select_files_1.png)

## Loading multiple experiments from a single file ##

If a file contains multiple experiments, metadata must be added about the partitioning of these experiments in the file so that these might be parsed properly. The data will then be split up during further processing into separate experiments.

![http://wiki.pepshell.googlecode.com/git/select_files_2.png](http://wiki.pepshell.googlecode.com/git/select_files_2.png)

## Adding metadata about files ##

If your file type is not supported automatically, it is possible to provide PepShell with the formatting of your file to have it import the data. This can be done by clicking the _add additional information…_ info button in the experiment selection window.

![http://wiki.pepshell.googlecode.com/git/meta_data_1.png](http://wiki.pepshell.googlecode.com/git/meta_data_1.png)

[Go to top of page](#Table_of_contents.md)

---

# Loading data from a database #

## Connecting to a proteomics database ##

PepShell supports data stored in an [ms-lims](https://code.google.com/p/ms-lims/) database out of the box, while [colims](https://code.google.com/p/colims/) support is currently being implemented as well.

For the inclusion of another database, you can contact the developers, or you can build your own importer if desired.

![http://wiki.pepshell.googlecode.com/git/login_2.png](http://wiki.pepshell.googlecode.com/git/login_2.png)

## Connecting to the (optional) structural database ##

PepShell can display structural data related to a protein, if there is a data source to provide these data. There is an in-house database implementation that was used as a proof-of-concept (and has been integrated into standard use). This is why there is a request for a second login; this can be ignored by pressing the _skip login_ button in case of absence of a structural database. Again, should you want to provide your own implementation, you can either contact the developers or build your own implementation.

![http://wiki.pepshell.googlecode.com/git/login_3.png](http://wiki.pepshell.googlecode.com/git/login_3.png)

If you do not have access to a structure database, just press the _skip login_ button and PepShell will try to retrieve as much information from external sources as possible. This will take you to the experiment selection screen.

[Go to top of page](#Table_of_contents.md)

---

# Selecting experiments #

Once the experimental data are loaded in PepShell, the experiment selection screen is presented, with the extracted experiments displayed in the list on the left hand side.

![http://wiki.pepshell.googlecode.com/git/select_projects_1.png](http://wiki.pepshell.googlecode.com/git/select_projects_1.png)

This window allows the selection of the reference experiment; i.e., the experiment that all other selected experiments will be compared to. Typically, the reference represents control conditions, while the other experiments represent different conditions, such as treatment with different molecules.

Experiments can also be grouped, for example in the case of experimental repeats.

## Grouping experiments ##

Grouping experiments in PepShell implies that these experiments should be treated as a single “meta” experiment (they can still be split up and be scrutinized separately after loading the data into PepShell) and allows for a more robust treatment of the acquired values by consolidating very similar data.

## Optional data loading steps ##

![http://wiki.pepshell.googlecode.com/git/select_projects_2.png](http://wiki.pepshell.googlecode.com/git/select_projects_2.png)

### PepShell offers optional loading steps ###

PepShell has two ways of retrieving additional data, dynamic and static. The main difference between these is that while fetching dynamic data, PepShell cannot display data that have not been downloaded yet. In the case of some web services, such data retrieval can easily take seconds. Meanwhile, PepShell will display the already available information and update the visualisations as soon as more data become available. In the optional loading steps (screenshot), static data retrieval can however be switched on, forcing online data retrieval to be executed first, and all retrieved data to be loaded in the program. In effect, we move all possible loading times to the front of the usage lifetime, lengthening start-up time but ensuring immediate display of all relevant data during use.

By default, PepShell will follow a standard series of steps to get the relevant information. These are (in the given order) conversion of the input accession numbers to UniProt accession numbers, adding domain information, adding protein database information, and finally executing a CP-DT analysis of the proteins (if CP-DT was previously allowed to be installed by PepShell).

### The order the steps are executed in ###

The order of execution can sometimes be important as the result of one step can rely on another. All optional loading steps are executed after PepShell has collected all the data about the experiments from the input files or database, and are executed sequentially as they are ordered in the optional loading steps window.

![http://wiki.pepshell.googlecode.com/git/data_retrieval.png](http://wiki.pepshell.googlecode.com/git/data_retrieval.png)

In the above example, the accession masking (forcing PepShell to display an accession number as a different accession number while maintaining the original accession number internally) is performed before the filtering step. This means that any masked protein will be treated as the masked accession number during the filtering step.

### Adding additional steps ###

Own defined steps can be added to PepShell. This is described in detail [here](DeveloperManual.md) with an example to follow.

[Go to top of page](#Table_of_contents.md)

---

# Experiment comparison tab page #

This tab page is primarily intended to make comparisons between the results of different experiments, and to provide a broader context to the collected data.

![http://wiki.pepshell.googlecode.com/git/comparison_main.png](http://wiki.pepshell.googlecode.com/git/comparison_main.png)

## Reference view ##

The reference part of the experiment comparison tab page contains all the information about the protein in the reference experiment, the peptide locations and their intensity (if any) and ratios (if any). It also shows secondary information to help paint a complete picture such as domain locations or theoretical cleavage sites, among others.

![http://wiki.pepshell.googlecode.com/git/comparison_reference.png](http://wiki.pepshell.googlecode.com/git/comparison_reference.png)

Data obtained from the PDB files are updated automatically when another available PDB file is selected for the protein in the dropdown menu. Note that the dropdown selection is only enabled when a secondary information source is chosen that actually uses the PDB file as a source.

The CP-DT analysis only shows the probable tryptic cleavage locations in the protein. Note that the peptide length is not shown.

## Experiment view ##

This view shows the selected protein (if it was found in the experiment), its peptides, and the peptide ratio or intensity.

![http://wiki.pepshell.googlecode.com/git/comparison_experiments.png](http://wiki.pepshell.googlecode.com/git/comparison_experiments.png)

## Sequence view ##

This view shows the sequence of the protein so the peptides can be seen in their amino acid context, along with domain annotations. Peptides are shown in blue, and domains are shown in orange. Peptides have priority over domains.

![http://wiki.pepshell.googlecode.com/git/comparison_sequence.png](http://wiki.pepshell.googlecode.com/git/comparison_sequence.png)

[Go to top of page](#Table_of_contents.md)

---

# Structure tab page #

The structure tab page shows the protein with its peptides in the known 3D conformations. It features the fully interactive Jmol 3D viewer (Herraez 2006). The peptides used for mapping are obtained from the selected experiment in the drop down menu.

The display can be exported to an image file, as well as the PDB file used.

![http://wiki.pepshell.googlecode.com/git/structure.png](http://wiki.pepshell.googlecode.com/git/structure.png)

[Go to top of page](#Table_of_contents.md)

---

# Statistics tab page #

This tab page shows various statistics about the selected protein, including a comparison between the ratios of every experiment, the specific comparison between two experiments, or the CP-DT cleavage probabilities for the theoretical peptides obtained from the protein sequence.

![http://wiki.pepshell.googlecode.com/git/statistics.png](http://wiki.pepshell.googlecode.com/git/statistics.png)

[Go to top of page](#Table_of_contents.md)

---

# Tweaking PepShell #

PepShell can be tweaked to behave more to your liking, or to expand upon its default features.

## Reporting a problem ##

Should you be experiencing problems with PepShell, please feel free to create a ticket on the Google Code page. The log file is expected to be extremely helpful in diagnosing the issue, so if there are no disclosure issues, please provide this file on ticket creation. This file can be found in the _!Pepshell/config_ folder.

## Memory management ##

On average, PepShell is pretty light on memory, but if the standard settings prove to be inadequate, they can be changed in the _JavaOpts_ file in the config folder (_Pepshellfolder/config/JavaOpts_)

The `–Xmx` option sets the maximum amount of memory PepShell is allowed to reserve. If PepShell should attempt to exceed this value, it will stop functioning. In such a rare case, PepShell will try to communicate this failure to allocate additional memory to the user.

## Disk space management ##

As PepShell uses temporary files (e.g., for CP-DT analyses), it requires at least some available disk space. Note that PepShell will clean up these temporary files after processing.

PepShell also creates some configuration files in the home folder of the current user (_~home/.compomics/pepshell_)

[Go to top of page](#Table_of_contents.md)

---

# Expanding on the base program (plugins and modding) #

For a detailed guide on how to expand the functionality and make alterations to the source code, please look [here](DeveloperManual.md).

[Go to top of page](#Table_of_contents.md)

---

# An example #

On the PepShell website, a real dataset which will be used in this section as a demo that walks the potential user through PepShell can be found. This dataset can also be found [here](http://genesis.ugent.be/pepshell/testfiles/).

## Biological background of the dataset ##

Protein kinases, which play key roles in cellular signalling events that mediate cell metabolism, growth, proliferation and differentiation, are the most frequently mutated oncogenes and tumor suppressors. Their dysregulation is frequently implicated in tumor initiation and progression (Blume-Jensen and Hunter, 2001). Over the past decade, several drugs targeting protein kinases have been approved for clinical use in cancer and many more are undergoing clinical trials. Most of these inhibitors are ATP-mimetic inhibitors which block the catalytic activity by binding to the ATP-binding cleft (Zhang et al., 2009). However, protein kinases are multi-domain proteins with a high degree of conformational plasticity. Recent studies have shown that binding of some ATP-mimetic inhibitors strengthens disease related kinase functions in spite of the fact that the catalytic activity is blocked. This might be because the full-length protein kinase is locked in a global active-like conformation which can activate other functional subdomains within the full-length kinase or trigger alterations in the subcellular localization which is an important factor of kinase function in general (Papa et al., 2003).

Within this example, we focus on B-Raf, a serine/threonine kinase that functions in the Ras-Raf-MEK-ERK mitogen-activated protein kinase pathway (Peyssonnaux and Eychene, 2001). B-Raf is composed of three conserved regions: a Ras-binding domain (RBD), a serine-rich hinge region, and a catalytic kinase domain (KD). Structures are available for the RBD and KD, but not for the full protein which is known to have a high degree of conformational plasticity. Several B-Raf inhibitors, such as vemurafenib, stabilize the active conformation and dimerization and as such, subsequently activate other proteins along the pathway. This results in the paradoxical activation of the MAPK pathway (Lavoie et al., 2013) and tumorigenesis triggering. Limited proteolysis combined with stable-isotope labelling mass spectrometry was used to analyse the conformational plasticity of B-Raf upon binding of small molecules and inhibitors (Di Michele et al., _in preparation_). This example is based on the experiments described by Di Michele and co-workers (_in preparation_).

## The dataset ##

The example dataset can be found among the test files at the project website. Here the dataset can be found divided over multiple files (limited proteolysis experiments divided over multiple files).

In our example, the dataset is given in a text-file with the information in tab-separated columns.

  * Column 1: sequence of the identified peptide

  * Column 2: gi accession of the associated protein

  * Column 3: start position of the peptide within the gi accession

  * Column 4: end position of the peptide within the gi accession

  * Column 5: total spectrum intensity

  * Column 6: ratio of light over heavy labelled peptides (see Di Michele and co-workers)

Two additional files, the annotation file and fasta-file of the recombinant B-Raf that were used in the experiments, are also provided. The annotation file provides more information on the column content. Its use will be explained below.

## Working with PepShell ##

PepShell can be retrieved through the _download PepShell_ button at the project website and requires Java 1.7 or higher. After unzipping the PepShell folder, PepShell is ready for usage by opening the _pepshell-program-0.9.jar_ file.

## Loading data into PepShell ##

Because this example is file based, this option is chosen to start PepShell.

![http://wiki.pepshell.googlecode.com/git/select_files_3.png](http://wiki.pepshell.googlecode.com/git/select_files_3.png)

The example data set can be downloaded as multiple files or as a single file. Here, we will illustrate the usage of multiple files. Therefore, because the reference experiment is in a separate file (_reference.txt_), this box has to be checked. The reference experiment and the comparison experiments can now be loaded:

![http://wiki.pepshell.googlecode.com/git/select_files_4.png](http://wiki.pepshell.googlecode.com/git/select_files_4.png)

In the case the data are in a single file, the _reference experiment in separate file_ box has to be unchecked. This allows the data to be loaded as a single file. Once the data are loaded, metadata can be added:

![http://wiki.pepshell.googlecode.com/git/meta_data_2.png](http://wiki.pepshell.googlecode.com/git/meta_data_2.png)

For the example data set, an annotation file can be loaded (_Annotation Files/load annotations_). This file contains the column information. These metadata can also be added manually. Next, the experiments to which the metadata apply have to be selected. In the case of the example data set, all experiments have to be selected. If several analyses with the same metadata have to be performed, these metadata can be saved (_Annotation Files/save annotations_). The metadata can then be accepted and finalized; and the selected files can next be validated. Within this validation step, all annotations are verified:

![http://wiki.pepshell.googlecode.com/git/meta_data_2.png](http://wiki.pepshell.googlecode.com/git/meta_data_2.png)

If there is an annotation error, re-annotation is possible by clicking on the error. Once all files passed the validation, one can continue; the data will now be prepared for further processing.

If the data are loaded and the metadata is added, the projects/experiments to analyse can be selected:

![http://wiki.pepshell.googlecode.com/git/select_projects_3.png](http://wiki.pepshell.googlecode.com/git/select_projects_3.png)

The reference project and the experiments needed can be selected. For this example, a recombinant B-Raf protein was used. The sequence of this protein can be loaded separately by checking the _use own fasta file_ box and adding the associated fasta-file which can be found together with the test dataset files.

## Visualization of the data ##

Once the data are loaded, they are ready for visualization. In our demo test set, we used our own fasta-file which contains the sequence of a recombinant B-Raf protein. This sequence can be found as accession `gi00000021`. To retrieve domain information of this accession, we have to re-annotate `gi00000021` to B-Raf. This can be done by: _view options/accession/set mask for accessions…_ UniProt ID `P15056` has to be chosen. This is the accession of human B-Raf.

![http://wiki.pepshell.googlecode.com/git/accession_masking.png](http://wiki.pepshell.googlecode.com/git/accession_masking.png)

![http://wiki.pepshell.googlecode.com/git/comparison_main_example.png](http://wiki.pepshell.googlecode.com/git/comparison_main_example.png)

The upper part of the panel shows the results of the reference experiments. In the demo test set, the reference experiment is a full digestion of human B-Raf.

![http://wiki.pepshell.googlecode.com/git/comparison_reference_example.png](http://wiki.pepshell.googlecode.com/git/comparison_reference_example.png)

The delineation of the known domains is shown in green. For B-Raf, these are the Ras Binding Domain (RBD) and the Protein Kinase Domain (KD). The identified peptides are mapped on the first bar. The second bar is colored according to the hydrophobicity of the residues. Other coloring options can be chosen.

The middle part of the panel gives the results of the separate experiments.

![http://wiki.pepshell.googlecode.com/git/comparison_experiments_example.png](http://wiki.pepshell.googlecode.com/git/comparison_experiments_example.png)

The experiment title can be changed by clicking on this title. The identified peptides can either be colored according to the peak intensity or light over heavy labelled ratio.

[Go to top of page](#Table_of_contents.md)

---

# References #

  * Blume-Jensen P, Hunter T (2001) Oncogenic kinase signaling. ''Nature'' 411, 355-365.
  * Di Michele M, Stes E, Vandermarliere E, Arora R, Astorga-Wells J, Vandenbussche J, van Heerde, E, Zubarev R, Bonnet P, Linders J, Jacoby E, Brehmer D, Martens L, Gevaert K (''In Preparation'') Limited proteolysis combined with stable isotope labelling reveals conformational changes in protein (pseudo)kinases upon binding small molecules.
  * Fannes T, Vandermarliere E, Schietgat L, Degroeve S, Martens L, Ramon J. (2013) Predicting tryptic cleavage from proteomics data using decision tree ensembles''. J Proteome Res'' 12 (5), 2253-2259.
  * Herraez A (2006) Biomolecules in the computer: Jmol to the rescue. ''Biochem Mol Biol Educ'' 34, 255-261.
  * Lavoie H, Thevakumaran N, Gavory G, Li JJ, Padeganeh A, Guiral S, Duchaine J, Mao DYL, Bouvier M, Sicheri F, Therrien M (2013) Inhibitors that stabilize a closed RAF kinase domain conformation induce dimerization. ''Nat Chem Biol'' 9, 428-436.
  * Papa FR, Zhang C, Shokat K, Walter P (2003) Bypassing a kinase activity with an ATP-competitive drug. ''Science'' 302, 1533-1537.
  * Peyssonnaux C, Eychene A (2001) The Raf/MEK/ERK pathway: new concepts of activation. ''Biol Cell'' 93, 53-62.
  * Zhang J, Yang PL, Gray NS (2009) Targeting cancer with small molecule kinase inhibitors. ''Nat Rev Cancer'' 9, 28-39.

[Go to top of page](#Table_of_contents.md)

---
