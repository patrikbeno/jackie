package org.jackie.jclassfile.flags;

/**
 * @author Patrik Beno
 */
public enum Access {

   PUBLIC(0x0001),
   FINAL(0x0010),
   SUPER(0x0020),
   INTERFACE(0x0200),
   ABSTRACT(0x0400),

   ;

   private int value;

   private Access(int value) {
      this.value = value;
   }

   public int value() {
      return value;
   }
}
