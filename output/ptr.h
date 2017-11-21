#pragma once

#include <iostream>
#include <string>

namespace __rt {

  template<typename T>
  class Ptr {
    T* addr;
    size_t* counter;

  public:
    template<typename U>
    friend class Ptr;

    // constructor to wrap raw pointer (and default constructor)
    Ptr(T* addr = 0) : addr(addr), counter(new size_t(1)) {
    }

    // copy constructor
    Ptr(const Ptr<T>& other) : addr(other.addr), counter(other.counter) {
      ++(*counter);
    }

    // conversion constructor
    template<typename U>
    Ptr(const Ptr<U>& other) : addr((T*) other.addr), counter(other.counter) {
      ++(*counter);
    }

    // destructor
    ~Ptr() {
      if (--(*counter) == 0) {
        if (addr != 0) {
          delete addr;
        }
        delete counter;
      }
    }

    // assignment operator
    Ptr& operator=(const Ptr& right) {
      if (addr != right.addr) {
        if (--(*counter) == 0) {
          if (addr != 0) {
            delete addr;
          }
          delete counter;
        }
        addr = right.addr;
        counter = right.counter;
        ++(*counter);
      }
      return *this;
    }

    T& operator*() const {
      return *addr;
    }

    T* operator->() const {
      return addr;
    }

    operator T*() const {
      return addr;
    }

    template<typename U>
    bool operator==(const Ptr<U>& other) const {
      return addr == (T*)other.addr;
    }
    
    template<typename U>
    bool operator!=(const Ptr<U>& other) const {
      return addr != (T*)other.addr;
    }

    T* raw() const { return addr; }

  };

}
