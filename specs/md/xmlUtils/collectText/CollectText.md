
# Collect all text contained in a parsed tree

One common task is to collect all text content for a tree or subtree:  that is, all text contained in a node and any of the nodes recursively contained by it. This requires collecting the String value of all contained text nodes in document order, while taking account of XML's treatment of white space outside of text nodes as not significant. The convention of the `XmlNode` class is to reduce spaces between elements to a single space character.


@openex@

### Example: recursive extraction ###

The first part of the text content of this well-formed XML fragment is found in direct children of the root element, but the document's text continues in hierarchically separated subelements.

<code concordion:set="#il1">&lt;l n="1">Sing, goddess, the rage of &lt;persName n="urn:cite:hmt:pers.pers1">Achilles&lt;/persName>&lt;/l></code>

When we extract the text from this node, we correctly get the single continuous string <strong concordion:assertEquals="collectText(#il1)">Sing, goddess, the rage of Achilles</strong>

@closeex@



@openex@
### Example: reduction of white space ###

Extra spaces in the XML document are regularized to a single white space character. In the following well-formed XML fragment, the text contents are identical to the preceding example, but white space is used differently in the markup:


<code concordion:set="#extrawhite">&lt;l n="1">Sing, goddess, the rage of  &lt;persName     n="urn:cite:hmt:pers.pers1"       >Achilles&lt;/persName>     &lt;/l>
</code>

When we extract the text contents from this node, we correctly get an identical string <strong concordion:assertEquals="collectText(#extrawhite)">Sing, goddess, the rage of Achilles</strong>
@closeex@


@openex@
### Example: adjacent elements ###

This example illustrates how the `collectText()` method separates strings extracted from distinct elements with a white space.  This markup markup is a possible encoding of (the beginning of) Shelley's *Ozymandias*. There is no white space at the end of the first poetic line and none at the beginning of the second line.


<code concordion:set="#twolines">&lt;div>&lt;l n="1">I met a traveller from an antique land&lt;/l>&lt;l n="2">Who said &lt;/l>&lt;/div></code>


`collectText()` correctly separates the last word of line 1 from the first word of line 2:

<strong concordion:assertEquals="collectText(#twolines)">I met a traveller from an antique land Who said</strong>



@closeex@

### Markup for explicit tokenization ###

Sometimes we want to override the default behavior of separating text content of adjacent elements.  The `XmlNode` class supports optionally defining markup conventions that cluster contained content into a single token with all white space removed.  Any of the following conventions may be used to define tokenizing markup:

1. A specified element, either namespace-qualified or not.
2. A specified attribute.
3. A specified value on a specified attribute.
4. A specified attribute on a specified element, either namespace-qualified or not.
5.  A specified value on a specified attribute on a specified element, either namespace-qualified or not.



@openex@

### Example: tokenize contents of a specified element  ###

Editors of historical documents might need to identify unclear sections, as illustrated in this example:


<code concordion:set="#unclear">&lt;l>Sing, &lt;w>god&lt;unclear>dess&lt;/unclear>&lt;/w>&lt;/l></code>


Extracting text context with default settings will yield the string
<strong concordion:assertEquals="collectText(#unclear)">Sing, god dess</strong>


If we define the <strong concordion:set="#magicelem">w</strong> element as containing a single token, we instead get the string <strong concordion:assertEquals="collectTextByElem(#magicelem,#unclear)">Sing, goddess</strong>


@closeex@



@openex@

### Example: tokenize contents identified by attribute  name ###

We can configure `XmlNode` to recognize tokenizing markup by attribute name. In the following example, the <code>w</code> element has an `ana` attribute.

<code concordion:set="#ana">&lt;l>Sing, &lt;w ana="token">god&lt;unclear>dess&lt;/unclear>&lt;/w>&lt;/l></code>


 If we extract the text with default settings, we again get the string <strong concordion:assertEquals="collectText(#ana)">Sing, god dess</strong>


If we define the <strong concordion:set="#magicattr">ana</strong> attribute as identifyiing a tokenizing element, we instead get the string <strong concordion:assertEquals="collectTextByAttrName(#magicattr,#ana)">Sing, goddess</strong>


@closeex@



@openex@

### Example: tokenize contents identified by attribute name + value ###

The following snippet has markup identifying a punctuation character, and uses the `ana` attributes with two different values.


<code concordion:set="#anaval">&lt;l>&lt;w ana="multitoken">Sing&lt;punct>,&lt;/punct>&lt;/w> &lt;w ana="token">god&lt;unclear>dess&lt;/unclear>&lt;/w>&lt;/l></code>




 If we extract the text with default settings, we again get the string <strong concordion:assertEquals="collectText(#anaval)">Sing , god dess</strong>



 We can configure `XmlNode` to recognize tokenizing markup by a combination of attribute name and value. Using the markup from the preceding example, we could set the <code concordion:set="#magicattname">ana</code> attribute to mark tokenizing elements *only* when it has the value <strong concordion:set="#magicval">token</strong>. When we collect the text from this sample, the contents of the element with `ana` attribute = `multitoken` will follow the default behavior of separating subelements, while the contents of the element with `ana` attribute = `token` will group that text in a single string with no whitespace, producing the string <strong concordion:assertEquals="collectTextByAttrValue(#magicattname,#magicval, #anaval)">Sing , goddess</strong>


@closeex@


@openex@

### Examples: limiting defining attributes to a specific element ###

In this example, we have an `ana` attribute with value `token` on two different elements.

<code concordion:set="#anavalelem">&lt;l>&lt;seg ana="token">Sing&lt;punct>,&lt;/punct>&lt;/seg> &lt;w ana="token">god&lt;unclear>dess&lt;/unclear>&lt;/w>&lt;/l></code>


Extracting the text with default settings, again yields the string <strong concordion:assertEquals="collectText(#anavalelem)">Sing , god dess</strong>


Now we add the restriction that the `token` value on `ana` only identifies tokenizing markup when on the element `w`.  The contents of the `seg` element will be broken up using the default rules, while the contents of the `w` element will be <strong concordion:assertEquals="collectTextByAttrValueOnElem(#magicelem,#magicattname,#magicval, #anaval)">Sing , goddess</strong>



@closeex@
