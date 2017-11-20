#pragma once

#include <iostream>
#include <string>

namespace __rt {

  template<typename T>
  class Ptr {
    T* addr;

  public:
    template<typename U>
    friend class Ptr;

    Ptr(T* addr = 0) : addr(addr) {
    }

    template<typename U>
    Ptr(const Ptr<U>& other) : addr((T*) other.addr) {
    }

    ~Ptr() {
    }

    Ptr& operator=(const Ptr& right) {
      if (addr != right.addr) {
        addr = right.addr;
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

    T* raw() const { return addr;  }

  };

}
