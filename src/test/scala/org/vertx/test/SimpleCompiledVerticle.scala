package org.vertx.test

import org.vertx.scala.deploy.Verticle
import org.vertx.java.core.buffer.Buffer
import org.vertx.java.core.eventbus.Message
import org.vertx.scala.core.http.HttpServerRequest
import org.vertx.scala.core.net.NetSocket
import org.vertx.scala.core.http.ServerWebSocket
import org.vertx.scala.core.http.HttpClientResponse
import org.vertx.java.core.json.JsonObject
import org.vertx.scala.core.sockjs.SockJSSocket

class SimpleCompiledVerticle extends Verticle {

  @throws(classOf[Exception])
  def start():Unit = {

    vertx
      .createNetServer
      .connectHandler({socket: NetSocket => 
        // socket
        socket.dataHandler({buffer: Buffer =>
          //
        })
        socket.drainHandler({() =>
          //
        })
        socket.endHandler({() =>
          //
        })
        socket.drainHandler({() =>
          //
        })

    }).listen(7080)

    vertx.createHttpServer.requestHandler({ req: HttpServerRequest => 
//      val file : String = if (req.path == "/") "/index.html" else req.uri
//      req.response.sendFile("webroot/" + file)
      req.response.end("hello scala!")
    }).listen(8080)

    // This looks weird, I'm probably doing something wrong.
    val closure = () => { Thread.sleep(2000L); print("hello ") }
    vertx.runOnLoop( closure )
    vertx.runOnLoop( () => { println("world") } )

    vertx.eventBus.send("test.echo", "echo!", (msg: Message[String]) => {
      printf("echo received: %s%n", msg.body)
    })

    vertx.sharedData.map("one")

    def http = vertx
      .createHttpServer
      .websocketHandler({s: ServerWebSocket => 
        s.writeTextFrame("foo")
        s.drainHandler({() =>
          //
          })
        s.dataHandler({data: Buffer => 
          //
          })
        })
      .listen(9090)

    val config = new JsonObject()
    vertx.createSockJSServer(http).installApp(config, { sock: SockJSSocket =>
      
    })

    println("compiled verticle started after hello world!")
  }

  @throws(classOf[Exception])
  override def stop(): Unit = {
    super.stop
    println("stopped!")
  }
}