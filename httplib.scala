package httplib

import _root_.scala.scalanative.unsafe.*
import _root_.scala.scalanative.unsigned.*
import _root_.scala.scalanative.libc.*
import _root_.scala.scalanative.*

object predef:
  private[httplib] trait _BindgenEnumCUnsignedInt[T](using eq: T =:= CUnsignedInt):
    given Tag[T] = Tag.UInt.asInstanceOf[Tag[T]]
    extension (inline t: T)
     inline def value: CUnsignedInt = eq.apply(t)
     inline def int: CInt = eq.apply(t).toInt
     inline def uint: CUnsignedInt = eq.apply(t)


object enumerations:
  import predef.*
  /**
   * [bindgen] header: resources/scala-native/httplib-glue.h
  */
  opaque type BodyType = CUnsignedInt
  object BodyType extends _BindgenEnumCUnsignedInt[BodyType]:
    given _tag: Tag[BodyType] = Tag.UInt
    inline def define(inline a: Long): BodyType = a.toUInt
    val JSON = define(0)
    val NONE = define(1)
    inline def getName(inline value: BodyType): Option[String] =
      inline value match
        case JSON => Some("JSON")
        case NONE => Some("NONE")
        case _ => _root_.scala.None
    extension (a: BodyType)
      inline def &(b: BodyType): BodyType = a & b
      inline def |(b: BodyType): BodyType = a | b
      inline def is(b: BodyType): Boolean = (a & b) == b

  /**
   * [bindgen] header: resources/scala-native/httplib-glue.h
  */
  opaque type Code = CUnsignedInt
  object Code extends _BindgenEnumCUnsignedInt[Code]:
    given _tag: Tag[Code] = Tag.UInt
    inline def define(inline a: Long): Code = a.toUInt
    val OK = define(200)
    val SERVER_ERROR = define(500)
    val BAD_REQUEST = define(400)
    inline def getName(inline value: Code): Option[String] =
      inline value match
        case OK => Some("OK")
        case SERVER_ERROR => Some("SERVER_ERROR")
        case BAD_REQUEST => Some("BAD_REQUEST")
        case _ => _root_.scala.None
    extension (a: Code)
      inline def &(b: Code): Code = a & b
      inline def |(b: Code): Code = a | b
      inline def is(b: Code): Boolean = (a & b) == b

object structs:
  import _root_.httplib.enumerations.*
  import _root_.httplib.predef.*
  import _root_.httplib.structs.*
  /**
   * [bindgen] header: resources/scala-native/httplib-glue.h
  */
  opaque type Handlers = CStruct2[CFuncPtr0[Ptr[Resp]], CFuncPtr2[CInt, CInt, Ptr[Resp]]]
  object Handlers:
    given _tag: Tag[Handlers] = Tag.materializeCStruct2Tag[CFuncPtr0[Ptr[Resp]], CFuncPtr2[CInt, CInt, Ptr[Resp]]]
    def apply()(using Zone): Ptr[Handlers] = scala.scalanative.unsafe.alloc[Handlers](1)
    def apply(list : CFuncPtr0[Ptr[Resp]], create : CFuncPtr2[CInt, CInt, Ptr[Resp]])(using Zone): Ptr[Handlers] = 
      val ____ptr = apply()
      (!____ptr).list = list
      (!____ptr).create = create
      ____ptr
    extension (struct: Handlers)
      def list : CFuncPtr0[Ptr[Resp]] = struct._1
      def list_=(value: CFuncPtr0[Ptr[Resp]]): Unit = !struct.at1 = value
      def create : CFuncPtr2[CInt, CInt, Ptr[Resp]] = struct._2
      def create_=(value: CFuncPtr2[CInt, CInt, Ptr[Resp]]): Unit = !struct.at2 = value

  /**
   * [bindgen] header: resources/scala-native/httplib-glue.h
  */
  opaque type Resp = CStruct3[Code, BodyType, Ptr[Byte]]
  object Resp:
    given _tag: Tag[Resp] = Tag.materializeCStruct3Tag[Code, BodyType, Ptr[Byte]]
    def apply()(using Zone): Ptr[Resp] = scala.scalanative.unsafe.alloc[Resp](1)
    def apply(code : Code, bodyType : BodyType, body : Ptr[Byte])(using Zone): Ptr[Resp] = 
      val ____ptr = apply()
      (!____ptr).code = code
      (!____ptr).bodyType = bodyType
      (!____ptr).body = body
      ____ptr
    extension (struct: Resp)
      def code : Code = struct._1
      def code_=(value: Code): Unit = !struct.at1 = value
      def bodyType : BodyType = struct._2
      def bodyType_=(value: BodyType): Unit = !struct.at2 = value
      def body : Ptr[Byte] = struct._3
      def body_=(value: Ptr[Byte]): Unit = !struct.at3 = value


@extern
private[httplib] object extern_functions:
  import _root_.httplib.enumerations.*
  import _root_.httplib.predef.*
  import _root_.httplib.structs.*
  /**
   * [bindgen] header: resources/scala-native/httplib-glue.h
  */
  def start_server(handlers : Ptr[Handlers], socket : CString, port : CInt): Unit = extern


object functions:
  import _root_.httplib.enumerations.*
  import _root_.httplib.predef.*
  import _root_.httplib.structs.*
  import extern_functions.*
  export extern_functions.*

object types:
  export _root_.httplib.structs.*
  export _root_.httplib.enumerations.*

object all:
  export _root_.httplib.enumerations.BodyType
  export _root_.httplib.enumerations.Code
  export _root_.httplib.structs.Handlers
  export _root_.httplib.structs.Resp
  export _root_.httplib.functions.start_server