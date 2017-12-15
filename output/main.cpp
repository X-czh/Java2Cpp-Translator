#include "output.h"

using namespace java::lang;

int main(int argc, char* argv[]) {
__rt::Array<String> args = new __rt::__Array<String>(argc - 1);

for (int32_t i = 1; i < argc; i++) {
(*args)[i] = __rt::literal(argv[i]);
}

inputs::test006::__Test006::main(args);

return 0;
}
