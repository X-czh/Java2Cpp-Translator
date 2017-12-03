#pragma once

#include <stdint.h>
#include <string>

#include "ptr.h"

// ==========================================================================

// To avoid the "static initialization order fiasco", we use functions
// instead of fields/variables for all pointer values that are statically
// initialized.

// See https://isocpp.org/wiki/faq/ctors#static-init-order

// ==========================================================================

namespace java {
  namespace lang {

    // Forward declarations of data layout and vtables.
    // This lets the compiler know that we have definitions forthcoming later in the program.
    // See http://www.learncpp.com/cpp-tutorial/17-forward-declarations/
    struct __Object;
    struct __Object_VT;

    struct __String;
    struct __String_VT;

    struct __Class;
    struct __Class_VT;

    // Definition of types that are equivalent to Java semantics,
    // i.e., an instance is the address of the object's data layout.
    typedef __rt::Ptr<__Object> Object;
    typedef __rt::Ptr<__Class> Class;
    typedef __rt::Ptr<__String> String;

  }
}

namespace __rt {

  // The function returning the canonical null value.
  java::lang::Object null();

  java::lang::String literal(const char*);

  // The template function for the virtual destructor.
  template <typename T>
  void __delete(T* addr) {
    delete addr;
  }

}


namespace java {
  namespace lang {

    // The data layout for java.lang.Object.
    // Think of this as roughly the 'properties' of java.lang.Object.
    struct __Object {
      // A pointer to a vtable which could differ at runtime from the static
      // reference we have to the Object vtable below. main.cc will demonstrate this.
      __Object_VT* __vptr;

      // The constructor.
      __Object();

      // The init method for the default constructor Object()
      static Object __init(Object __this) { return __this; }
      
      // The methods implemented by java.lang.Object.
      static int32_t hashCode(Object);
      static bool equals(Object, Object);
      static Class getClass(Object);
      static String toString(Object);

      // The function returning the class object representing java.lang.Object.
      static Class __class();

      // The vtable for java.lang.Object itself.
      // Moreover, always a reference to the behaviours of java.lang.Object.
      static __Object_VT __vtable;
    };

    // The vtable layout for java.lang.Object.
    // Think of this as roughly the 'methods' of java.lang.Object.
    struct __Object_VT {
      // The dynamic type, main.cc will demonstrate this.
      Class __is_a;

      // These properties are function pointers, the syntax:
      // ex:   int32_t     (*sum)          (int32_t, int32_t);
      //       return_type (*function_name)(arg_type_list);
      // See http://www.learncpp.com/cpp-tutorial/78-function-pointers/
      void (*__delete)(__Object*);
      int32_t (*hashCode)(Object);
      bool (*equals)(Object, Object);
      Class (*getClass)(Object);
      String (*toString)(Object);

      // The vtable constructor. Notice that it is initializing
      // the function pointer properties with references to the
      // static implementations provided by Object itself.
      // This is how "subclasses" will "inherit" from "superclasses"
      __Object_VT()
      : __is_a(__Object::__class()),
        __delete(&__rt::__delete<__Object>),
        hashCode(&__Object::hashCode),
        equals(&__Object::equals),
        getClass(&__Object::getClass),
        toString(&__Object::toString) {
      }
    };

    // ======================================================================

    std::ostream& operator<<(std::ostream& os, String s);

    // The data layout for java.lang.String.
    struct __String {
      __String_VT* __vptr;

      // The member that contains the actual string data.
      std::string data;

      // The constructor
      __String(std::string data);
      
      // The init method for the constructor String()
      static String __init(String __this) { return __this; }

      // The init method for the constructor String(String)
      static String __init(String __this, std::string data) { __this->data = data; return __this; }
      
      // The methods implemented by java.lang.String.
      static int32_t hashCode(String);
      static bool equals(String, Object);
      static String toString(String);
      static int32_t length(String);
      static char charAt(String, int32_t);

      // The function returning the class object representing java.lang.String.
      static Class __class();

      // The vtable for java.lang.String.
      static __String_VT __vtable;
    };

    template<typename T>
    String safeToString(T t) {
      return __rt::null() == t ? __rt::literal("null") : t->__vptr->toString(t);
    }

    template<typename S, typename T>
    String operator+(__rt::Ptr<S> s, __rt::Ptr<T> t) {
      return new __String(safeToString(s)->data + safeToString(t)->data);
    }

    String operator+(String s, char t); 

    String operator+(char s, String t); 

    // The vtable layout for java.lang.String.
    struct __String_VT {
      // The dynamic type.
      Class __is_a;

      void (*__delete)(__String*);
      int32_t (*hashCode)(String);
      bool (*equals)(String, Object);
      Class (*getClass)(String);
      String (*toString)(String);
      int32_t (*length)(String);
      char (*charAt)(String, int32_t);

      __String_VT()
      : __is_a(__String::__class()),
        __delete(&__rt::__delete<__String>),
        hashCode(&__String::hashCode),
        equals(&__String::equals),
        getClass((Class(*)(String)) &__Object::getClass), // "inheriting" getClass from Object
        toString(&__String::toString),
        length(&__String::length),
        charAt(&__String::charAt) {
      }
    };

    // ======================================================================

    // Class is a little special in that all other classes will be 'composed' with
    // a Class instance. Its purpose is to encapsulate type information about a runtime 'instance'.
    // See http://docs.oracle.com/javase/7/docs/api/java/lang/Class.html

    // The data layout for java.lang.Class.
    struct __Class {
      __Class_VT* __vptr;
      String name;
      Class parent;
      Class component;
      bool primitive;

      // The constructor.
      __Class(String name,
              Class parent,
              Class component = __rt::null(),
              bool primitive = false);

      // The init method for the constructor Class()
      static Class __init(Class __this) { return __this; }
      
      // The instance methods of java.lang.Class.
      static String toString(Class);
      static String getName(Class);
      static Class getSuperclass(Class);
      static bool isPrimitive(Class);
      static bool isArray(Class);
      static Class getComponentType(Class);
      static bool isInstance(Class, Object);

      // The function returning the class object representing java.lang.Class.
      static Class __class();

      // The vtable for java.lang.Class.
      static __Class_VT __vtable;
    };

    // The vtable layout for java.lang.Class.
    struct __Class_VT {
      // The dynamic type.
      Class __is_a;

      void (*__delete)(__Class*);
      int32_t (*hashCode)(Class);
      bool (*equals)(Class, Object);
      Class (*getClass)(Class);
      String (*toString)(Class);
      String (*getName)(Class);
      Class (*getSuperclass)(Class);
      bool (*isPrimitive)(Class);
      bool (*isArray)(Class);
      Class (*getComponentType)(Class);
      bool (*isInstance)(Class, Object);

      __Class_VT()
      : __is_a(__Class::__class()),
        __delete(&__rt::__delete<__Class>),
        hashCode((int32_t(*)(Class)) &__Object::hashCode),
        equals((bool(*)(Class,Object)) &__Object::equals),
        getClass((Class(*)(Class)) &__Object::getClass),
        toString(&__Class::toString),
        getName(&__Class::getName),
        getSuperclass(&__Class::getSuperclass),
        isPrimitive(&__Class::isPrimitive),
        isArray(&__Class::isArray),
        getComponentType(&__Class::getComponentType),
        isInstance(&__Class::isInstance) {
      }
    };

    // ======================================================================


    // For simplicity, we use C++ inheritance for exceptions and throw
    // them by value.  In other words, the translator does not support
    // user-defined exceptions and simply relies on a few built-in
    // classes.
    class Throwable {
    };

    class Exception : public Throwable {
    };

    class RuntimeException : public Exception {
    };

    class NullPointerException : public RuntimeException {
    };

    class NegativeArraySizeException : public RuntimeException {
    };

    class ArrayStoreException : public RuntimeException {
    };

    class ClassCastException : public RuntimeException {
    };

    class IndexOutOfBoundsException : public RuntimeException {
    };

    class ArrayIndexOutOfBoundsException : public IndexOutOfBoundsException {
    };

  }
}

// ==========================================================================

namespace __rt {
  // Function for converting a C string literal to a translated
  // Java string.
  inline java::lang::String literal(const char * s) {
    // C++ implicitly converts the C string to a std::string.
    return new java::lang::__String(s);
  }

  // ========================================================================

  // Forward declarations of data layout and vtable.
  template <typename T>
  struct __Array;

  template <typename T>
  struct __Array_VT;

  // generic C++ 'typedef' for representing Java array types,
  // i.e., Array<T> corresponds to Java's T[].
  template <typename T>
  using Array = __rt::Ptr<__Array<T> >;

  // The data layout for arrays.
  template <typename T>
  struct __Array {
    __Array_VT<T>* __vptr;
    const int32_t length;
    T* __data;

    // The constructor (defined inline).
    __Array(const int32_t length)
      : __vptr(&__vtable), length(length), __data(new T[length]()) {
    }

    // The destructor.
    static void __delete(__Array<T>* addr) {
      delete[] addr->__data;
      delete addr;
    }
    
    // overload array subscript operators for convenient bounds-checked array access
    T& operator[](int32_t index)  {
      if (0 > index || index >= length)
        throw java::lang::ArrayIndexOutOfBoundsException();
      return __data[index];
    }

    const T& operator[](int32_t index) const {
      if (0 > index || index >= length)
        throw java::lang::ArrayIndexOutOfBoundsException();
      return __data[index];
    }

    // The function returning the class object representing the array.
    static java::lang::Class __class();

    // The vtable for the array.
    static __Array_VT<T> __vtable;
  };

  // But where is the definition of __Array::__class()???

  // Generic partial specialization of __Array for arrays of object types.
  // This saves us the extra work of manually specializing the __class()
  // method for arrays of object types.
  template <typename T>
  struct __Array<Ptr<T>> {
    __Array_VT<Ptr<T>>* __vptr;
    const int32_t length;
    Ptr<T>* __data;

    // The constructor (defined inline).
    __Array(const int32_t length)
        : __vptr(&__vtable), length(length), __data(new Ptr<T>[length]()) {
    }

    // The destructor.
    static void __delete(__Array<Ptr<T>>* addr) {
      delete[] addr->__data;
      delete addr;
    }

    // overload array subscript operators for convenient bounds-checked array access
    Ptr<T>& operator[](int32_t index)  {
      if (0 > index || index >= length)
        throw java::lang::ArrayIndexOutOfBoundsException();
      return __data[index];
    }

    const Ptr<T>& operator[](int32_t index) const {
      if (0 > index || index >= length)
        throw java::lang::ArrayIndexOutOfBoundsException();
      return __data[index];
    }

    // The function returning the class object representing the array.
    static java::lang::Class __class() {
      static java::lang::Class k =
          new java::lang::__Class(new java::lang::__String("[L" + T::__class()->name->data + ";"),
                                  java::lang::__Object::__class(),
                                  T::__class());
      return k;
    }

    // The vtable for the array.
    static __Array_VT<Ptr<T>> __vtable;
  };

  // The vtable for arrays.
  template <typename T>
  struct __Array_VT {
    typedef Array<T> Reference;

    java::lang::Class __is_a;
    void (*__delete)(__Array<T>*);
    int32_t (*hashCode)(Reference);
    bool (*equals)(Reference, java::lang::Object);
    java::lang::Class (*getClass)(Reference);
    java::lang::String (*toString)(Reference);

    __Array_VT()
      : __is_a(__Array<T>::__class()),
        __delete(&__Array<T>::__delete),
        hashCode((int32_t(*)(Reference))
                 &java::lang::__Object::hashCode),
        equals((bool(*)(Reference,java::lang::Object))
               &java::lang::__Object::equals),
        getClass((java::lang::Class(*)(Reference))
                 &java::lang::__Object::getClass),
        toString((java::lang::String(*)(Reference))
                 &java::lang::__Object::toString) {
    }
  };

  // The vtable for arrays.  Note that this definition uses the default
  // no-arg constructor.
  template <typename T>
  __Array_VT<T> __Array<T>::__vtable;

  template <typename T>
  __Array_VT<Ptr<T>> __Array<Ptr<T>>::__vtable;

  // ========================================================================

  // Template functions for run-time checks go below.

  // Template function to check against null values.
  template <typename T>
  void checkNotNull(T object) {
    if (null() == object) {
      throw java::lang::NullPointerException();
    }
  }

  // Template function to check array access is within bounds.
  template <typename T>
  void checkIndex(Array<T> array, int32_t index) {
    if (0 > index || index >= array->length) {
      throw java::lang::ArrayIndexOutOfBoundsException();
    }
  }

  // Template function to check array stores.
  template <typename T, typename U>
  void checkStore(Array<T> array, U object) {
    if (null() != (java::lang::Object) object) {
      java::lang::Class t1 = array->__vptr->getClass(array);
      java::lang::Class t2 = t1->__vptr->getComponentType(t1);

      if (! t2->__vptr->isInstance(t2, (java::lang::Object) object)) {
        throw java::lang::ArrayStoreException();
      }
    }
  }

  template<typename T, typename U>
  T java_cast(U object) {
    java::lang::Class c = T::value_type::__class();

    if (!c->__vptr->isInstance(c, object))
      throw java::lang::ClassCastException();

    return T(object);
  }
  
}
