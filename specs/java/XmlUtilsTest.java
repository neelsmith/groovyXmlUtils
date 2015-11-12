
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import edu.holycross.shot.xmlutils.XmlNode;

@RunWith(ConcordionRunner.class)
public class XmlUtilsTest {


  public String yo() {
    return ("Yo.");
  }


  public int echoSize(String s) {
    return (s.length());
  }

  public String collectText(String xmlSrc) {
    XmlNode n = new XmlNode(xmlSrc);
    return (n.collectText());
  }
}
