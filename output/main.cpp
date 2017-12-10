#include <iostream>

#include "ptr.h"
#include "java_lang.h"
#include "output.h"

using namespace java::lang;
using namespace std;
int main(int argc, char* argv[]) {
__rt::Array<String> args = new __rt::__Array<String>(argc - 1);

for (int32_t i = 1; i < argc; i++) {
(*args)[i] = __rt::literal(argv[i]);
}

inputs::test000::__Test000::main(args);

return 0;
}
