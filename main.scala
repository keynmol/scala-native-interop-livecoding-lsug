//> using scala 3.7.2-RC1
//> using platform scala-native
//> using nativeVersion 0.5.8
//> using resourceDir resources
//> using nativeCompile -fcxx-exceptions
//> using dep com.lihaoyi::upickle::4.2.1

import httplib.all.*
import scala.scalanative.unsafe.*
import upickle.default.*
import httplib.enumerations.Code.BAD_REQUEST
import scala.collection.mutable.ArrayBuffer

case class Order(width: Int, height: Int) derives Writer
case class Curtain(width: Int, height: Int) derives Writer

case class State(orders: ArrayBuffer[Order], stock: ArrayBuffer[Curtain])
    derives Writer

object Globals:
  val z = Zone.open()
  val state = State(ArrayBuffer.empty[Order], ArrayBuffer.empty[Curtain])

def rectangle_fits_scala(curt: Curtain, width: Int, height: Int): Boolean =
  curt.width >= width && curt.height >= height

@main def hello =
  import Globals.*

  val handlers = Handlers()(using z)

  import state.*

  stock += Curtain(100, 240)

  (!handlers).list = CFuncPtr0.fromScalaFunction: () =>
    given Zone = Globals.z
    Resp(Code.OK, BodyType.JSON, toCString(write(state)))

  (!handlers).create = CFuncPtr2.fromScalaFunction: (width: Int, height: Int) =>
    given Zone = Globals.z

    val code = util.boundary:
      stock.zipWithIndex.foreach: (curtain, idx) =>
        if rectangle_fits_scala(curtain, width, height) then
          orders += Order(width, height)
          stock(idx) = Curtain(curtain.width - width, curtain.height - height)
          util.boundary.break(Code.OK)
      util.boundary.break(BAD_REQUEST)

    Resp(code, BodyType.NONE, null)

  start_server(handlers, c"localhost", 8899)
end hello
