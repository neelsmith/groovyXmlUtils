package edu.holycross.shot.xmlutils

import groovy.xml.Namespace

/**
 */
class XmlNode {

  /** Temporary variable to delete before release version. */
  Integer debug = 0


  // Values defining a magic element wrapping word tokens potentially including
  //further markup that extraction needs to burrow through
  /** If non-null, magic element must be in this namespace */
  String magicNs = ""
  /** If non-null, local name of magic word wrapping element. */
  String magicNode = ""
  /** If non-null, name of an attribute that must be present on magicNode. */
  String magicAttrName = ""
  /** If non-null, required value for magic attribute named by magicAttrName. */
  String magicAttrValue = ""
  /** Category of tokenizing markup to apply to this node. */
  TokenizingMarkup magicMarkup = null

  /** The root of the XML content as a parsed groovy.util.Node */
  def parsedNode = null



  /**
   * Constructs a XmlNode object from a groovy Node object.
   */
  XmlNode (groovy.util.Node n) {
    parsedNode = n
  }

  /** Constructs XmlNode object from a String of well-formed XML
  *  by parsing the string and saving the resulting root node.
  * @param content A String of well formed XML.
  */
  XmlNode (String content) {
    try {
      parsedNode = new XmlParser().parseText(content)
    } catch (Exception e) {
      throw new Exception("XmlNode: could not parse content ${content}")
    }
    if (!parsedNode) {
      throw new Exception("XmlNode: could not parse content ${content}")
    }
  }


  /** Single method to assign values to all settings defining
  * behavior of magic node.  Based on supplied values, determines what
  * category of TokenizingMarkup applies to this node.
  * @param ns XML namespace for magic node.
  * @param Local name of magic node.
  * @param Name of magic attribute.
  * @param Value of magic attribute.
  */
  void setMagic(String ns, String nodeName, String attrName, String attrValue) {
     magicNs = ns
     magicNode = nodeName
     magicAttrName = attrName
     magicAttrValue = attrValue

     if ((nodeName.size() > 0)) {
       // Node name therefore requird:
      if ((attrName.size() > 0) && (attrValue.size() > 0)) {
        magicMarkup = TokenizingMarkup.ATTRIBUTE_VALUE_ON_ELEMENT
      } else  if ((nodeName.size() > 0) && (attrName.size() > 0)) {
        magicMarkup = TokenizingMarkup.ATTRIBUTE_ON_ELEMENT
      } else {
        magicMarkup = TokenizingMarkup.ELEMENT_ONLY
      }
    } else {
      // attribute on any node:
      if ((attrName.size() > 0 ) && (attrValue.size() > 0)) {
        magicMarkup = TokenizingMarkup.ATTRIBUTE_VALUE_ONLY
      } else if ((attrName.size() > 0 )) {
        magicMarkup = TokenizingMarkup.ATTRIBUTE_ONLY
      }
    }
  }



  /** Determines whether the name of a given node matches
  * the current setting for a magic tokenizing node.  The node
  * name may either be a simple name, or a QName qualified by an
  * XML namespace.
  * @param n The node to check.
  * @returns True if n matches the current setting for magic node.
  */
  boolean checkElementName(Node n) {
    boolean nameIsMagic = false
    def elementName = n.name()
    if (elementName instanceof java.lang.String) {
      if (elementName == magicNode) {
        nameIsMagic = true
      }
    } else {
      if ((elementName.getNamespaceURI().toString() == magicNs) && (elementName.getLocalPart() == magicNode)) {
        nameIsMagic = true
      }
    }
    return nameIsMagic
  }


  /** Determines whether the name of a given attribute matches
  * the current setting for a magic tokenizing node.  The node
  * name may either be a simple name, or a QName qualified by an
  * XML namespace.
  * @param n The node to check.
  * @returns True if n matches the current setting for magic node.
  */
  boolean checkAttributeName(Node n) {
    if ((magicAttrName.size() > 0) && (n.attribute(magicAttrName))) {
      return true
    } else {
      return false
    }
  }


  /** Determines whether the name/value combination of a given attribute matches
  * the current setting for a magic tokenizing node.  The node
  * name may either be a simple name, or a QName qualified by an
  * XML namespace.
  * @param n The node to check.
  * @returns True if n matches the current setting for magic node.
  */
  boolean checkAttributeNameValue(Node n) {
    if (
        (magicAttrName.size() > 0) &&
        (n.attribute(magicAttrName)) &&
        (magicAttrValue.size() > 0) &&
        (n.attribute(magicAttrName) == magicAttrValue)
    ) {
      return true
    } else {
      return false
    }
  }


  /** Determines whether a given node contains text contents to be extracted
  * as a single token.
  * @param n The node to check.
  * @returns True if node is a magic node containing text to tokenize as a unit.
  */
  boolean magicNode(Node n) {
    boolean magic = false
    switch (this.magicMarkup) {
      case TokenizingMarkup.ELEMENT_ONLY:
      magic = checkElementName(n)
      break
      case TokenizingMarkup.ATTRIBUTE_ONLY:
      magic = checkAttributeName(n)
      break

      case  TokenizingMarkup.ATTRIBUTE_VALUE_ONLY:
      magic = checkAttributeNameValue(n)
      break
      case  TokenizingMarkup.ATTRIBUTE_ON_ELEMENT:
      break
      case  TokenizingMarkup.ATTRIBUTE_VALUE_ON_ELEMENT:
      break


      default:
      // leave magic false
      break
    }
    return magic
  }

  /** Recursively walks through all descendants of the XmlNode's
  * root element, and collects the content of text nodes. In handling white space,
  * XML elements are taken to mark new, white-space delimited tokens
  * except where markup identified by the magicNode() method
  * groups together a token with mixed content model.
  * @return A String with the text content of the object node.
  */
  String collectText() {
    return collectText(this.parsedNode,"", false)
  }

  /** Recursively walks through all descendants of an XML node
   * and collects the content of text nodes. In handling white space,
   * XML elements are taken to mark new, white-space delimited tokens
   * except where markup identified by the magicNode() method
   * groups together a token with mixed content model.
   * @param n The parsed node from which text will be extracted.
   * @return A String with the text content of the object node.
   */
  String collectText(groovy.util.Node n) {
    return collectText(n,"", false)
  }

  /** Recursively walks through all descendants of an XML node
   * and collects the content of text nodes. In handling white space,
   * XML elements are taken to mark new, white-space delimited tokens
   * except where markup identified by the magicNode() method
   * groups together a token with mixed content model.
   * @param n The parsed node from which text will be extracted.
   * @param allText The String of previously accumulated text content,
   * to which the content of any further text nodes will be added.
   * @param inWord Flag indicating whether or not we're within a "magic" node
   * with mixed content model.
   * @return A String with the text content of the object node.
   */
  String collectText(Object n, String allText, boolean inWord) {
    String collectedText = allText
    boolean inMagic = inWord
    if (n instanceof java.lang.String) {
      String stripped = n.replaceFirst(/[\s\r\n]+$/, "")
      collectedText +=  stripped

    } else {
      boolean isMagic = (magicNode(n))
      if  (isMagic ){
        inMagic = true
      }
      n.children().each { child ->
        if (!inMagic) {
          collectedText += " "
        }
        collectedText = collectText(child, collectedText,inMagic)
      }
      if (isMagic) {
        inMagic = false
      }
    }
    return collectedText.replaceFirst(/^[\s\n\r]/, "").replaceAll(/[\s\n\r]+/," ")
  }





  /** Serializes the object's parsedNode as an XML String.
   * This serialization does not include any XML or namespace declarations.
   * @return A String of well-formed XML.
   */
  String toXml() {
    return serializeNode(parsedNode, "", false)
  }

  /** Serializes the object's parsedNode as an XML String.
   * @param withDefaultNsDecl True if namespace declaration
   * should be included.
   * @return A String of well-formed XML.
   */
  String toXml(boolean withDefaultNsDecl) {
    return serializeNode(parsedNode, "", false, withDefaultNsDecl)
  }



  /* ************************************************************/
  // PRIVATE METHODS FOR FORMING XML STRINGS /////////////////////

  private String closeElement(Object n) {
    if (n.name() instanceof groovy.xml.QName)  {
      if (n.name().getPrefix().size() > 0) {
	return "</${n.name().getPrefix()}:${n.name().getLocalPart()}>"
      } else {
	return "</${n.name().getLocalPart()}>"
      }
    } else {
      return "</" + n.name() + ">"
    }
  }

  private String openElement(Object n, boolean withDefaultNs) {
    StringBuffer tag = new StringBuffer()
    if (n.name() instanceof groovy.xml.QName) {
      if (n.name().getPrefix().size() > 0) {
	if (withDefaultNs) {
	  tag.append('<?xml version="1.0" encoding="UTF-8"?>\n')
	}
	tag.append("<${n.name().getPrefix()}:${n.name().getLocalPart()}")
	if (withDefaultNs) {
	  tag.append(" xmlns='" + n.name().getNamespaceURI() + "' ")
	}
	tag.append(" xmlns:${n.name().getPrefix()}=" + '"' + n.name().getNamespaceURI() + '" ')

      } else {
	tag.append("<${n.name().getLocalPart()}")
	if (withDefaultNs) {
	  tag.append(' xmlns="' + n.name().getNamespaceURI() + '" ')
	}

      }
    } else {
      tag.append("<" + n.name())
    }
    tag.append( collectAttrs(n))
    tag.append(">")
    return tag.toString()
  }


  private String serializeNode(Object n, String allText, boolean inWord) {
    return serializeNode(n, allText, inWord, false)
  }


  /** Creates a String of well-formed XML by recursively walking the
   * descendants of groovy.util.Node.
   * @param n A node resulting from XmlParser's output, which could be either
   * a groovy.util.Node object or a String object.
   * @param allText The String of previously accumulated text content,
   * to which the content of any further text nodes will be added.
   * @return A String of well-formed XML.
   */
  private String serializeNode(Object n, String allText, boolean inWord, boolean includeRootNsDecl) {
    if (n instanceof java.lang.String) {
      allText = allText + n
    } else {
      allText += openElement(n, includeRootNsDecl)
      n.children().each { child ->
	allText += " "
      }
      allText = serializeNode(child, allText,inWord)
    }

    allText+=  closeElement(n)
    return allText
  }


  /** Creates a String representing any attributes of a given node
   * in the form key="value"
   * @param n The Node to examine.
   * @return A String with space-separated key="value" pairs.
   */
  private String collectAttrs(groovy.util.Node n) {
    StringBuffer attrStr = new StringBuffer()
    n.attributes().keySet().each { a ->
      if (a instanceof groovy.xml.QName) {
	attrStr.append(" ${a.getPrefix()}:${a.getLocalPart()}=" + '"' + n.attribute(a) + '" ')
      } else {
	attrStr.append(" ${a}=" + '"' + n.attribute(a) + '" ')
      }
    }
    return attrStr.toString()
  }

}
