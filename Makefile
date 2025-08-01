bindings:
	sn-bindgen --header src/main/resources/scala-native/httplib-glue.h \
	--package httplib --scala --out src/main/scala/httplib.scala -- \
	# Flags that will be passed to clang
	# -xc++ tells it to interpret code as C++
	-xc++
