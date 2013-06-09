#>>lang=cf

class WebsocketHelper

  constructor: (@location = "ws://localhost:8080/dmk-websocket/echo") ->
    @ws = undefined

  connect = (@location) ->
      target = @location
      ws  = new  WebSocket(target);
      console.log ws
      
      ws.onmessage =  (event) ->
          console.log "Received:#{event.data}"
          json = JSON.parse event.data
          console.log "parsed = #{json.m1}"
      ws.onopen = () ->
          console.log "opened connection"
          ws.send "syn connection"
      ws.onclose = () ->
          console.log "closed connection"
      ws

    # if not connected, connects to websocket endpoint
    # turns object passed into JSON
    # sends JSON to currently connected websocket endpoint
    send: (@obj) ->
      if not @ws?
          console.log "websocket is not initialized..."
          @ws = connect(@location)
          console.log @ws
      else
          console.log "reusing websocket connection"
          tmp = JSON.stringify @obj
          @ws.send @obj
          
    close: () ->
      @ws.close() and @ws = undefined if @ws?