#include "java_lang.h"

#include <stdexcept>
#include <sstream>
#include <iostream>

namespace java {
  namespace lang {

    // java.lang.Object()
    __Object::__Object() : __vptr(&__vtable) {}

    // java.lang.Object.hashCode()
    int32_t __Object::hashCode(Object __this) {
      return (int32_t)(intptr_t) __this;
    }

    // java.lang.Object.equals(Object)
    bool __Object::equals(Object __this, Object other) {
      return __this == other;
    }

    // java.lang.Object.getClass()
    Class __Object::getClass(Object __this) {
      return __this->__vptr->__is_a;
    }

    // java.lang.Object.toString()
    String __Object::toString(Object __this) {
      // Class k = this.getClass();
      Class k = __this->__vptr->getClass(__this);

      std::ostringstream sout;
      sout << k->__vptr->getName(k)->data
           << '@' << std::hex << (uintptr_t) __this;
      return new __String(sout.str());
    }

    // Internal accessor for java.lang.Object's class.
    Class __Object::__class() {
      static Class k =
        new __Class(__rt::literal("java.lang.Object"), (Class) __rt::null());
      return k;
    }

    // The vtable for java.lang.Object.  Note that this definition
    // invokes the default no-arg constructor for __Object_VT.
    __Object_VT __Object::__vtable;

    // =======================================================================

    // java.lang.String(<literal>)
    __String::__String(std::string data)
      : __vptr(&__vtable), 
        data(data) {
    }

    // java.lang.String.hashCode()
    int32_t __String::hashCode(String __this) {
      int32_t hash = 0;

      // Use a C++ iterator to access string's characters.
      for (std::string::iterator itr = __this->data.begin();
           itr < __this->data.end();
           itr++) {
        hash = 31 * hash + *itr;
      }

      return hash;
    }

    // java.lang.String.equals()
    bool __String::equals(String __this, Object o) {
      // Make sure object is a string:
      // if (! o instanceof String) return false;
      Class k = __String::__class();
      if (! k->__vptr->isInstance(k, o)) return false;

      // Do the actual comparison.
      String other = (String) o; // Downcast.
      return __this->data.compare(other->data) == 0;
    }

    // java.lang.String.toString()
    String __String::toString(String __this) {
      return __this;
    }

    // java.lang.String.length()
    int32_t __String::length(String __this) {
      return (int32_t) __this->data.length();
    }

    // java.lang.String.charAt()
    char __String::charAt(String __this, int32_t idx) {
      if (0 > idx || (unsigned) idx >= __this->data.length()) {
        throw std::out_of_range("Index out of bounds for string " + __this->data);
      }

      // Use std::string::operator[] to get character without
      // duplicate range check.
      return __this->data[idx];
    }

    // Internal accessor for java.lang.String's class.
    Class __String::__class() {
      static Class k =
        new __Class(__rt::literal("java.lang.String"), __Object::__class());
      return k;
    }

    // The vtable for java.lang.String.  Note that this definition
    // invokes the default no-arg constructor for __String_VT.
    __String_VT __String::__vtable;

    // =======================================================================

    // java.lang.Class(String, Class)
    __Class::__Class(String name, Class parent, Class component, bool primitive)
      : __vptr(&__vtable),
        name(name),
        parent(parent),
        component(component),
        primitive(primitive) {
    }

    // java.lang.Class.toString()
    String __Class::toString(Class __this) {
      return new __String("class " + __this->name->data);
    }

    // java.lang.Class.getName()
    String __Class::getName(Class __this) {
      return __this->name;
    }

    // java.lang.Class.getSuperclass()
    Class __Class::getSuperclass(Class __this) {
      return __this->parent;
    }

    // java.lang.Class.isPrimitive()
    bool __Class::isPrimitive(Class __this) {
      return __this->primitive;
    }

    // java.lang.Class.isArray()
    bool __Class::isArray(Class __this) {
      return (Class)__rt::null() != __this->component;
    }

    // java.lang.Class.getComponentType()
    Class __Class::getComponentType(Class __this) {
      return __this->component;
    }

    // java.lang.Class.isInstance(Object)
    bool __Class::isInstance(Class __this, Object o) {
      // isInstance traverses the inheritance hierarchy upwards
      // (until it hits null) to determine whether an object
      // is an instance of a given class
      Class k = o->__vptr->getClass(o);

      do {
        if (__this->__vptr->equals(__this, (Object)k)) return true;

        // FIXME: handle covariance of arrays
        // If both __this and k represent array types, then we have to check
        // whether the component type of k is a subtype of the component
        // type of __this.
        if (__this->__vptr->isArray(__this) && k->__vptr->isArray(k)) {
          // k != __this implies that the component type of k cannot
          // be equal to the component type of __this. So it is OK to
          // go directly to the superclass of k's component type and
          // continue the traversal of the type hierarchy from there.
          k = k->__vptr->getComponentType(k);
          __this = __this->__vptr->getComponentType(__this);
        }
        
        k = k->__vptr->getSuperclass(k);
      } while ((Class)__rt::null() != k);

      return false;
    }

    // Internal accessor for java.lang.Class' class.
    Class __Class::__class() {
      static Class k = 
        new __Class(__rt::literal("java.lang.Class"), __Object::__class());
      return k;
    }

    // The vtable for java.lang.Class.  Note that this definition
    // invokes the default no-arg constructor for __Class_VT.
    __Class_VT __Class::__vtable;

  }
}

// ===========================================================================

namespace __rt {

  // The function returning the canonical null value.
  java::lang::Object null() {
    static java::lang::Object value(0); // init the pointer type to 0, the 'null pointer'
    return value;
  }

  // Template specialization for arrays of ints.
  template<>
  java::lang::Class __Array<int32_t>::__class() {
    // The Class object representing int.class
    static java::lang::Class ik =
        new java::lang::__Class(__rt::literal("int"),
                                (java::lang::Class) __rt::null(),
                                (java::lang::Class) __rt::null(),
                                true);
    // The Class object representing int[].class
    static java::lang::Class k =
      new java::lang::__Class(literal("[I"),
                              java::lang::__Object::__class(),
                              ik);
    return k;
  }

  // Template specialization for arrays of objects.
  template<>
  java::lang::Class __Array<java::lang::Object>::__class() {
      static java::lang::Class k =
      new java::lang::__Class(literal("[Ljava.lang.Object;"),
                              java::lang::__Object::__class(),
                              java::lang::__Object::__class());
    return k;
  }

  // Template specialization for arrays of strings.
  template<>
  java::lang::Class __Array<java::lang::String>::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[Ljava.lang.String;"),
                              java::lang::__Object::__class(),
                              java::lang::__String::__class());
    return k;
  }

}
