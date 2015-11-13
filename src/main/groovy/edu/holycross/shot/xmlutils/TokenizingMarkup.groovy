package edu.holycross.shot.xmlutils

/** A class enumerating the categories of markup identifying
* text content to take as a single token.
*/
public enum TokenizingMarkup {

  /** Category where specific element tokenizes. */
  ELEMENT_ONLY ("Category where any occurrence of specified element tokenizes"),
  /** Category where specific attribute tokenizes, regardless of element it's on. */
  ATTRIBUTE_ONLY("Category where any occurrence of specified attribute tokenizes"),
  /** Category where specific value on specific attribute tokenizes, regardless of element it's on. */
  ATTRIBUTE_VALUE_ONLY("Category where ny occurrence of specified value on specified attribute tokenizes"),
  /** Category where specific attribute on specific element tokenizes, regardless of element it's on. */
  ATTRIBUTE_ON_ELEMENT("Category where any occurrence of specified attribute on specified element tokenizes"),
  /** Category where specific attribute on specific attribute on specific element tokenizes. */
  ATTRIBUTE_VALUE_ON_ELEMENT("Category where any occurrence of specified value of specified attribute on specified element tokenizes")

  private String label

  private TokenizingMarkup(String label) {
    this.label = label
  }

  /** Gets a human-readable label for this value. */
  public String getLabel() {
    return label
  }
}
