#include "output.h"

using namespace java::lang;

int main(int argc, char* argv[]) {
__rt::Array<String> args = new __rt::__Array<String>(argc - 1);

for (int32_t i = 1; i < argc; i++) {
(*args)[i - 1] = __rt::literal(argv[i]);
}

inputs::test::__Test::main(args);

return 0;
}
