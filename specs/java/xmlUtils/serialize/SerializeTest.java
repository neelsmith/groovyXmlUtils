package xmlUtils.serialize;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import edu.holycross.shot.xmlutils.XmlNode;

@RunWith(ConcordionRunner.class)
public class SerializeTest {

  public String serialize(String xmlSrc) {
    XmlNode n = new XmlNode(xmlSrc);
    return (n.toXml());
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
