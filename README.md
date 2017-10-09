# Document Analysis Platform

What it is:
------------
The **Document-Analysis Platform**, or **DAP**, is a programming platform for integrating several NLP tools, making them:
* interact with each other, and
* conform to the same interface.

**DAP** is a lightweight, simple and easy-to-use alternative to [UIMA](https://uima.apache.org/). While UIMA is a revolutionary and strong platform, it suffers from significant drawbacks, which turned into high barriers for new-comers.

The need for a simple, easy-to-learn and easy-to-use alternative, which preserves only the core ideas of UIMA, is the motivation behind DAP development.

The advantages of DAP over UIMA are:
* UIMA takes several weeks to learn, and requires reading of hundreds of user-manuals pages. Getting started with DAP takes no longer than 5-10 minutes. Learning DAP 100% A-to-Z takes only 20 minutes.
* UIMA requires long and hard-to-maintain XML files. DAP requires nothing but pure-Java programming.
* UIMA employs unusual paradigms for exception throwing, logging, constructing objects, etc. DAP follows normal Java conventions.

The core idea
----------------
NLP tools tend to depend on each other. _Part-of-speech_ taggers operate over _tokenized_ texts. _Syntactic parsers_ operate over _part-of-speech_ annotations. _Coreference-resolvers_ operate over _syntactic analyses_. etc. In short, higher level tools rely on the output of lower-level ones.

This brings up the challenge of _integration_. Both the syntactic-parser and the part-of-speech tagger should agree on the data-structures and the format of a POS-tagged text. In other words, the POS-tagger output should be what the syntactic-parser expects. This requirement applies to every set of tools with dependencies between them.

Moreover, if all POS-taggers conform to the same format, then replacing one tagger by another is transparent to the syntactic-parser. Similarly, if all the parsers conform to the same format, then replacing one parser by another is transparent to the coreference-resolver.

The goal of **DAP** is to target this integration challenge. DAP provides data-structures with characteristics and utilities that make them fit for virtually every standard NLP tool. The main two data-structures are `document` and `annotation`. The output of every NLP tool can be stored as `annotation`s in `document`s, with features, attributes, and inter-annotation relations.

In addition to data-structures, an actual set of part-of-speech tags, syntactic phrases types, syntactic-dependency-relations, etc. is required. The project [DAP-DKPro_1_8](https://github.com/document-analysis/dap-dkpro_1_8) provides a standard set of NLP types, borrowing them from the [DKPro](https://dkpro.github.io/) project.


Batteries included
----------------------
Users can start working with DAP right-away with dozens of state-of-the-art NLP tools for several languages, by using the [DAP-DKPro_1_8](https://github.com/document-analysis/dap-dkpro_1_8) library, which wraps DKPro tools inside DAP.

A demo is provided in [DAP-DKPro_1_8-demo](https://github.com/document-analysis/dap-dkpro_1_8-demo).


Your first steps
------------------
Start by reading the [20-minutes-tutorial](https://github.com/document-analysis/dap/blob/master/20_minutes_tutorial.md).

Then jump to the [demo](https://github.com/document-analysis/dap-dkpro_1_8-demo).

License
---------
DAP is licensed under Apache 2.0 license, which is a permissive license that is good also for commercial use.

Note that [DAP-DKPro_1_8-demo](https://github.com/document-analysis/dap-dkpro_1_8-demo) depends on external libraries, which have more restrictive licenses.

