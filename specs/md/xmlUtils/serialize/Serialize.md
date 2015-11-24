# Serialize a parsed tree or subtree#

The task of re-serializing a parsed node and its contents to XML is tedious.  One complication is that in some contexts, it may be desirable to include explicit XML namespace definitions if serializing a subtree when the XML namespace is defined in a containing element.  The `XmlNode` class supports recursively serializing a node with or without the addition of an explicit namespace.  The resulting XML string is XML-equivalent to the string source of the parsed node, with occurrences of whitespace normalized to a single space character.

Attempting to serialize a node with an explicit namespace when the source XML has no namespace declaration is an error.


@openex@

### Example: serializing a node with no name space ###

This XML snippet has no namespace declaration:

<code concordion:set="#il1">&lt;l n="1">Sing, goddess, the rage of &lt;persName n="urn:cite:hmt:pers.pers1">Achilles&lt;/persName>&lt;/l></code>

If we create an `XmlNode` from it, and then serialize the node to an XML string,
we get  


<strong concordion:assertEquals="serialize(#il1)">&lt;l n="1"> Sing, goddess, the rage of &lt;persName n="urn:cite:hmt:pers.pers1"> Achilles&lt;/persName>&lt;/l></strong>




@closeex@


@openex@

### Example: adding the default name space to the serialization ###


This fragment of well-formed XML declares as its default namespace the namespace of the Text Encoding Initiative:

<code concordion:set="#il1ns">&lt;div xmlns="http://www.tei-c.org/ns/1.0">&lt;l n="1">Sing, goddess>&lt;/l>&lt;/div></code>

If we serialize the root element with its default namespace, we unsurprisingly get the XML-equivalent String:

<strong concordion:assertEquals="serializeWithNS(#il1ns)">&lt;div xmlns="http://www.tei-c.org/ns/1.0"> &lt;l n="1"> Sing, goddess>&lt;/l>&lt;/div></strong>

If we serialize the child `l` element separately, the well-formed fragment maintains the default namespace:

<strong concordion:assertEquals="serializeChildWithNS(#il1ns)">&lt;l  xmlns="http://www.tei-c.org/ns/1.0" n="1"> Sing, goddess>&lt;/l></strong>



@closeex@


## Interaction of serializing and collecting text ##

Because the serialization of a parsed node is XML-equivalent to the source for the parsed node, the output of the `collectText` method on any `XmlNode` created from the serialization is guaranteed to be identical to the output of `collectText` on the original parsed node, so long as any configuration of tokenizing markup is applied to both original and derivative node.  (Refer to the [specification for collecting text in a parsed tree for details about tokenizing markup](../collectText/CollectText.html).)


@openex@

### Example: equivalence of text contents of parsed node and serialized String ###

Consider a parsed node created from the following well-formed XML fragment:

<code concordion:set="#ana">&lt;l>Sing, &lt;w ana="token">god&lt;unclear>dess&lt;/unclear>&lt;/w>&lt;/l></code>

If we collect the text contents of this node, we get
<strong concordion:assertEquals="collectText(#ana)">Sing, god dess</strong>.  If we define the <strong concordion:set="#magicelem">w</strong> element as tokenizing markup, `collectText` instead gets <strong concordion:assertEquals="collectTextByElem(#magicelem, #ana)">Sing, goddess</strong>, following the [specification for collecting text](../collectText/CollectText.html).


If we serialize the node, we get an XML-equivalent string with slightly different white space: <strong concordion:assertEquals="serialize(#ana)">&lt;l> Sing, &lt;w ana="token"> god &lt;unclear> dess&lt;/unclear>&lt;/w>&lt;/l></strong>

If we create a new `XmlNode` from this XML-equivalent fragment,

<code concordion:set="#deriv">&lt;l> Sing, &lt;w ana="token"> god &lt;unclear> dess&lt;/unclear>&lt;/w>&lt;/l></code>

using `collectText` produces identical results.  That is, the default settings would collect  <strong concordion:assertEquals="collectText(#deriv)">Sing, god dess</strong>, but if we define the **w** element as tokenizing markup, `collectText` instead gets <strong concordion:assertEquals="collectTextByElem(#magicelem, #deriv)">Sing, goddess</strong>.


@closeex@
