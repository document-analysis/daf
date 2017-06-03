15 Minutes tutorial
===================

This tutorial covers 100% of the _Document Analysis Platform_

The Basics
----------

* A _Document_ is a basically a text, i.e., a string.
* An _Annotation_ is a span - a marked portion of the document. Additional user-defined information is typically attached to an annotation.
* An _Annotator_ is a piece of code that processes documents. Typically, this processing is mainly adding new annotations to the documents.

For example, assume we have a document:
>"This is a document"

We can, for example, add an annotation over the word "is". That annotation has _begin-index_, which specifies the index of the annotation's first character. Character-indexes start at 0, so the letter "T" of "This" has the index 0. The letter "i" of "is" has the index 5. The annotation also has _end-index_, which specifies the index of the first character right after the annotation. In our case, the end index is 7.

The begin and end indexes define the annotation's _covered-text_. In our case: "is".

Additional information, a.k.a. _annotation-contents_, can be added to an annotation. This annotation-contents is totally user-defined. For example one can define _annotation-contents_ with the word-number in the document. In our case, "is" is the second word in the document, i.e., word-number 2. Other examples of annotation-contents are part-of-speech tag (for example, "auxiliary-verb"), lemma ("be"), etc.

_Annotation-contents_ may also include _reference_ to another annotation. This is useful in, for example, co-reference annotation, in which the annotations of all the mentions of the same entity contain references to each other.

Public API
----------
Public API are classes, methods and fields for which backward compatibility is guaranteed within the same _major version_. The _document Analysis Platform_ follows the conventions of [SemVer](http://semver.org/), where versions are numbered as `x.y.z` (`x` indicates the major version).

All the public API are annotated in the code with the java-annotation `DapAPI`. It is strongly recommended not to use any class, method or field that is not annotated by that annotation, as they can be removed in newer versions.

Documents
---------

The class `org.dap.data_structures.Document` represents a document. A document is constructed with a _name_ and _text_. The name is just a user-defined name. The text is the document's text. Both the name and the text cannot be changed.
Example:
```java
Document myDocument = new Document("myFirstDocument", "This is a document");
```
The name and the text can then be retrieved by `getName()` and `getText()` respectively.

### Work with Annotations

Annotations are added by the method `addAnnotation()`. The method takes the begin and end indexes, and an instance of a user-defined `AnnotationContents` subclass.
Example:
```java
public class MyAnnotationContents extends AnnotationContents
{
	public MyAnnotationContents(wordNumber)
	{
		this.wordNumber = wordNumber;
	}
	
	// getter and setter code here
	
	private int wordNumber;
}

AnnotationReference reference = document.addAnnotation(5, 7, new MyAnnotationContents(2));
```

Note that the `addAnnotation()` method returns an `AnnotationReference`. This is an object that uniquely identifies the annotation that has been added. No two annotations share the same `AnnotationReference`, even if they are identical.
Example:
```java
AnnotationReference reference1 = document.addAnnotation(5, 7, new MyAnnotationContents(2));

AnnotationReference reference2 = document.addAnnotation(5, 7, new MyAnnotationContents(2));

System.out.println(reference1.equals(reference2)); // Will print "false".
```

To get the actual annotation that has been added to the document, use the method `findAnnotation()`.
Example:
```java
Annotation<?> annotation = document.findAnnotation(reference);
System.out.println(annotation.getBegin()); // Will print "5"
System.out.println(annotation.getEnd()); // Will print "7"
System.out.println(annotation.getCoveredText()); // Will print "is"
System.out.println(annotation.getAnnotationReference().equals(reference)); // Will print "true"
```

If you want to refer to one annotation in the `AnnotationContents` of another annotation, the correct way to do that is to store its `AnnotationReference`.
Example:
```java
public class CorefMentionAdditionalInformation extends AnnotationContents
{
	// Constructor, getter and setter here.
	
	private Set<AnnotationReference> referencesToOtherMentions;
}
```

Note that __Annotation is immutable__: non of its fields can be changed. You can, however, remove an annotation from a document.

An annotation can be removed by
```java
document.removeAnnotation(reference);
```
To find out if an annotation exists (it has been added and has not been removed) use:
```java
document.isAnnotationExist(reference);
```

### Iterating over Annotations

The most important functionality of `Document` is the __ordered__ iteration over the `Document`'s annotations.
The `Document` class itself is an `Iterable`. It provides an `Iterator`over its annotations by the method `iterator()`. This iterator iterates over all of the document's annotations, in the following order:

For `Annotation` `x` and `Annotation` `y`:

* if `x.getBegin()<y.getBegin()` then `x` comes before `y` (and vice versa).
* if `x.getBegin()==y.getBegin()` then if `x.getEnd()<y.getEnd()` then `x` comes before `y` (and vice versa).
* If `x.getBegin()==y.getBegin()` and `x.getEnd()==y.getEnd()`, then their ordering is arbitrary.

In many cases we would like to iterate over only some kind of annotations, not over all the annotations.
For example, we have annotated the document with sentences (annotations that cover entire sentences) and also annotated the document with tokens. Now, we want to iterate over the sentences. To do that, we must distinguish between these two kinds of annotations when adding them to the document, by defining different `AnnotationContents` classes for each. Then, we can use the `AnnotationContents` class-object as an argument for the `iterator()` method.

Example:
```java
public class Token extends AnnotationContents
{
}
public class Sentence extends AnnotationContents
{
}
Document document = new Document("my document", "This is a document.");
document.addAnnotation(0, 19, new Sentence());
document.addAnnotation(0, 4, new Token());
document.addAnnotation(5, 7, new Token());
document.addAnnotation(8, 9, new Token());
document.addAnnotation(10, 18, new Token());
```

Now, when calling
```java
document.iterator(Sentence.class);
```
an iterator that iterates only over the sentences will be returned.

To make coding more convenient, several `iterable()` methods are provided, which return an `Iterable` which returns the desired `iterator`.

Continuing the example above:
```java
for (Annotation<?> annotation : document.iterable(Sentence.class))
{
	System.out.println(annotation.getCoveredText());
}
```
will print:
```text
This is a document.
```

```java
for (Annotation<?> annotation : document.iterable(Token.class))
{
	System.out.println(annotation.getCoveredText());
}
```
will print
```text
This
is
a
document
```

And just for the sake of completeness:
```java
for (Annotation<?> annotation : document)
{
	System.out.println(annotation.getCoveredText());
}
```
will print
```text
This
This is a document.
is
a
document
```

It is also possible to narrow down the iteration to a portion of the document, by providing the arguments `begin` and `end` to the `iterator()` and `iterable()` methods. These arguments specify that only annotations that begin where specified by the `begin` parameter or afterwards, and end where specified by the `end` parameter or before, will be returned.

Example:
```java
for (Annotation<?> annotation : document.iterable(AnnotationContents.class, 0, 8))
{
	System.out.println(annotation.getCoveredText());
}
```
will print
```text
This
is
```

### Document Features
In addition to annotations, documents may also have _features_, which hold some general information about the document. Features are totally user-defined, by implementing the interface `org.dap.data_structures.Feature`.

Document features have name, by which they can be retrieved. Adding a feature to a document is performed as follows:
```java
myDocument.setFeature("some cool name", myFeature);
```
The Document's `getFeatures()` returns an immutable map with all the features. Thus,  a feature can be retrieved by:
```java
Feature myFeature = document.getFeatures().get("some cool name");
```
To remove a feature, use the method `removeFeature()`.

#### LanguageFeature
One of the most common usages of a feature is the language feature, which describes the document's language (e.g., English, French, etc.). Because of being so common, the platform provides the implementation `LanguageFeature`.

Two static methods help to conveniently set and get a document's language:
```java
LanguageFeature.setDocumentLanguage()
``` 
and
```java
LanguageFeature.getDocumentLanguage()
```


Document Collection
-------------------
In some scenarios we would like to annotate two texts that are related to each other. For example, a document with translations to several other languages. Assume we have the same document in English, French and Spanish. We could use something like
```java
Map<String, Document>
```
which is a map from each language to the document in that language.

However, things get complicated if we want to store references in an annotation in one document to other annotations that might reside in other documents. For example, we could want to annotate some entity, and in each annotation store references to all of the entity's mentions in all of the documents. As in the usual case of co-reference annotations, we would store a set of `AnnotationReference`s. But if we do that, how would we retrieve an actual annotation from such `AnnotationReference`? If we just call `englishDocument.findAnnotation()` with such an `AnnotationReference`, we might get an exception, since that annotation does not exist in the English document, but in the French document. (Even worse, we could get some totally unrelated annotation).

The platform addresses such scenarios with the mechanism of `DocumentCollection`. A `DocumentCollection` is a collection of documents, where each of them has its distinguishing name.

Here are some facts you need to know about `DocumentCollection`

1. When a `Document` is created, it is added into a `DocumentCollection`. The `DocumentCollection` is specified as an argument in the `Document`'s constructor. If that argument is omitted, a default new `DocumentCollection` is created for the document.
2. A document cannot be "moved" to another `DocumentCollection`. One the document has been constructed and added to a `DocumentCollection`, it will belong to that `DocumentCollection` forever.
3. The `DocumentCollection` of a document can be retrieved by `Document`'s method `getDocumentCollection()`.
4. The `Document`'s `findAnnotation()` method can get references to annotations that reside in another document, as long as that document belongs to the same `DocumentCollection`. This behavior can be changed by setting the method's argument `inThisDocumentOnly` to `true`.
5. The same applies to `isAnnotationExist()`.
6. However, iterators iterate only over the annotations in the document itself.

Annotator
---------
An _Annotator_ is a piece of code that processes documents. In most scenarios this processing would be adding new annotations to the documents (hence the name "_Annotator_"), but additional operations, like setting features, printing the document's text to a file, etc., can be performed by an annotator as well.

`org.dap.annotators.Annotator` is the superclass for all the annotators. _DAP_ users will mainly develop annotators.

Example:
```java
public class MyAnnotator extends Annotator
{
	@Override
	public void annotate(Document document)
	{
		// user code here
	}
}
```

### Aggregate Annotator
An `AggregateAnnotator` is simply an `Annotator` which is constructed with a list of `Annotator`s, and runs them sequentially for each document. In other words, its `annotate()` method calls the `annotate()` methods of all the annotators it has been constructed with.

### Split and Merge Annotator
