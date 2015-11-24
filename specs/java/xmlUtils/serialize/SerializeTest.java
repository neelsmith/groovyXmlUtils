package xmlUtils.serialize;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import groovy.util.Node;
import java.util.List;

import edu.holycross.shot.xmlutils.XmlNode;

@RunWith(ConcordionRunner.class)
public class SerializeTest {

  public String serialize(String xmlSrc) {
    XmlNode n = new XmlNode(xmlSrc);
    return (n.toXml());
  }



  public String serializeWithNS(String xmlSrc) {
    XmlNode n = new XmlNode(xmlSrc);
    return (n.toXml(true));
  }


  public String serializeChildWithNS(String xmlSrc) {
    XmlNode n = new XmlNode(xmlSrc);
    List<Object> childNodes = n.getParsedNode().children();
    Object groovyChild = childNodes.get(0);
    XmlNode childNode = new XmlNode((groovy.util.Node) groovyChild);
    //return("GROOVY CHILD: " + groovyChild.toString() + " to " + childNode) ;
    return (childNode.toXml(true));
  }



  public String collectText(String xmlSrc) {
    XmlNode n = new XmlNode(xmlSrc);
    return (n.collectText());
  }

  public String collectTextByElem(String elementName, String xmlSrc) {
    XmlNode n = new XmlNode(xmlSrc);
    n.setMagic("", elementName, "", "");
    return (n.collectText());
  }
}
