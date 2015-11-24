package xmlUtils.collectText;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import edu.holycross.shot.xmlutils.XmlNode;

@RunWith(ConcordionRunner.class)
public class CollectTextTest {

  public String collectText(String xmlSrc) {
    XmlNode n = new XmlNode(xmlSrc);
    return (n.collectText());
  }


  public String collectTextByElem(String elementName, String xmlSrc) {
    XmlNode n = new XmlNode(xmlSrc);
    n.setMagic("", elementName, "", "");
    return (n.collectText());
  }


  public String collectTextByAttrName(String attrName, String xmlSrc) {
    XmlNode n = new XmlNode(xmlSrc);
    n.setMagic("", "", attrName, "");
    return (n.collectText());
  }

  public String collectTextByAttrValue(String attrName, String attrValue, String xmlSrc) {
    XmlNode n = new XmlNode(xmlSrc);
    n.setMagic("", "", attrName, attrValue);
    String collected = n.collectText();
    return (collected);
  }

  public String collectTextByAttrValueOnElem(String elemName, String attrName, String attrValue, String xmlSrc) {
    XmlNode n = new XmlNode(xmlSrc);
    n.setMagic("", elemName, attrName, attrValue);
    String collected = n.collectText();
    return (collected);
  }



}
