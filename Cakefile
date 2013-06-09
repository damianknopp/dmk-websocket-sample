fs = require 'fs'
{exec} = require 'child_process'

src = "src/main/webapp"
dest = "src/main/webapp"
lessc = "lessc"

# clean
task 'clean', "Clean project build dir #{dest}", ->
  console.log "Cleaning build directory #{dest}..."
  if fs.existsSync dest
    deleteFolderRecursive dest
  else
    console.log "Nothing to do, #{dest} does not exist in #{process.cwd()}"

# build
task 'build', 'Build project from #{src}/*.coffee to #{dest}/*.js', ->
  #if fs.existsSync dest
  #  throw "#{dest} already exists, please clean first"
  #else
	#console.log "creating #{dest}"
	#fs.mkdirSync "./#{dest}"

  compileCoffee = "coffee --compile --output #{dest} #{src}"
  console.log compileCoffee
  exec compileCoffee, (err, stdout, stderr) ->
    throw err if err
    console.log "done building #{src}" + stdout + stderr
  compileAllLessFiles src

# simple pass to the shell call `rm -rf`
rmrf = (path) ->
  rm = "rm -rf #{path}"
  console.log rm
  exec rm

#deleteFolderRecursive = delfolderSync
###
# unlink and rmdir using the node.js API, 
# uses the Sync version of the calls
delfolderSync = (path) ->
  files = []
  if fs.existsSync(path) 
    files = fs.readdirSync(path)
    files.forEach( (file,index) ->
    curPath = path + "/" + file
    if fs.statSync(curPath).isDirectory()
      deleteFolderRecursive curPath
    else 
      fs.unlinkSync curPath
    )
    fs.rmdirSync path
###

# delete a folder and its folders/files
deleteFolderRecursive = rmrf

# generate less files recursively
# walks the base dir recursively using the node.js synchronous API
compileAllLessFiles = (path) ->
  files = []
  if fs.existsSync(path) 
    files = fs.readdirSync(path)
    files.forEach( (file,index) ->
      curPath = "#{path}/#{file}"
      if fs.statSync(curPath).isDirectory()
        compileAllLessFiles curPath
      else 
        if curPath.endsWith ".less"
          console.log "found less file #{path} #{curPath}"
          basePath = path.removePrefix src
          basePath = "#{dest}#{basePath}"
          compiledFile = "#{basePath}/#{file.removeSuffix('.less')}.css"
          if not fs.existsSync basePath
            console.log "creating #{basePath}"
            fs.mkdirSync "./#{basePath}"
          compileLessCmd = "#{lessc} #{curPath} > #{compiledFile}"
          console.log compileLessCmd
          exec compileLessCmd
          
    )


String::endsWith = (suffix) ->
  suffix.length is this.length - this.lastIndexOf suffix

String::startsWith = (prefix) ->
  this.indexOf prefix is 0

String::removePrefix = (prefix) ->
  if this.startsWith prefix
    this.substring prefix.length, this.length
  else
    throw "not a valid prefix! #{prefix}"

String::removeSuffix = (suffix) ->
  if this.endsWith suffix
    this.substring 0, this.length - suffix.length
  else
    throw "not a valid suffix! #{suffix}"
