#pragma once

#include <cstring>

#if 0
#include <iostream>
#define TRACE(s) \
  std::cout << __FUNCTION__ << ":" << __LINE__ << ":" << s << std::endl
#else
#define TRACE(s)
#endif

namespace __rt {

  template<typename T>
  struct java_policy {
    static void destroy(T* addr) {
      addr->__vptr->__delete(addr);
    }
  };

  template<typename T>
  struct object_policy {
    static void destroy(T* addr) {
      delete addr;
    }
  };

  template<typename T>
  struct array_policy {
    static void destroy(T* addr) {
      delete[] addr;
    }
  };

  template<typename T, template<typename> class P = java_policy>
  class Ptr {
    T* addr;
    size_t* counter;

  public:
    typedef P<T> policy_type;
    typedef T value_type;

    template<typename U, template<typename> class Q>
    friend class Ptr;

    // constructor to wrap raw pointer (and default constructor)
    Ptr(T* addr = 0) : addr(addr), counter(new size_t(1)) {
      TRACE(addr);
    }

    // copy constructor
    Ptr(const Ptr& other) : addr(other.addr), counter(other.counter) {
      TRACE(addr);
      ++(*counter);
    }

    // conversion constructor
    template<typename U>
    Ptr(const Ptr<U, P>& other) : addr((T*) other.addr), counter(other.counter) {
      TRACE(addr);
      ++(*counter);
    }

    // destructor
    ~Ptr() {
      TRACE(addr);
      if(0 == --(*counter)) {
        if (0 != addr) {
          TRACE("delete addr");
          policy_type::destroy(addr);
        }
        delete counter;
      }
    }

    // assignment operator
    Ptr& operator=(const Ptr& right) {
      TRACE(addr);
      if (addr != right.addr) {
        if (0 == --(*counter)) {
          if (addr != 0) policy_type::destroy(addr);
          delete counter;
        }
        addr = right.addr;
        counter = right.counter;
        ++(*counter);
      }
      return *this;
    }

    
    T& operator*()  const { TRACE(addr); return *addr; }
    T* operator->() const { TRACE(addr); return addr;  }
    T* raw()        const { TRACE(addr); return addr;  }

    template<typename U>
    bool operator==(const Ptr<U>& other) const {
      return addr == (T*)other.addr;
    }
    
    template<typename U>
    bool operator!=(const Ptr<U>& other) const {
      return addr != (T*)other.addr;
    }

  };

}
