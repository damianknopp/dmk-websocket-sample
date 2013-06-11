#>>lang=cf

class window.WebsocketHelper
  constructor: (@location = "ws://localhost:8080/dmk-websocket/echo") ->
    @ws = undefined

  connect: (@location) ->
    target = @location
    @ws  = new WebSocket(target)
    console.log @ws
    
    @ws.onmessage = (event) ->
      console.log "#{event.data}"
      json = JSON.parse event.data
      console.log json
  
    @ws.onopen = () =>
      console.log "opened connection"
      msg = msg: "hello"
      this.send msg
  
    @ws.onclose = () ->
      console.log "closed connection"
    
    @ws

  # if not connected, connects to websocket endpoint
  # turns object passed into JSON
  # sends JSON to currently connected websocket endpoint
  send: (obj) ->
    if not @ws?
      console.log "websocket is not initialized..."
      @ws = this.connect(@location)
      console.log @ws
    else
      console.log "reusing websocket connection"
      tmp = JSON.stringify obj
      @ws.send tmp
          
  close: () ->
    @ws.close() and @ws = undefined if @ws?