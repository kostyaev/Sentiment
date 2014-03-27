messages = null
progress = null
rest = null
messageWithId = null
index = 0
size = 0

loadData = () -> $.get "/data", (jsdata) ->
  messages = jsdata.messages
  progress = jsdata.progress
  rest = jsdata.rest
  size = messages.length
  index = 0
  update()

update = () ->
  if index >= size then loadData()
  else
    messageWithId = messages[index]
    $(".message_class").html messageWithId.message
    $(".message_class").attr "id", messageWithId.id
    $("#countDone").html progress++
    $("#countTodo").html rest--
    $("#result-progress").css "width", (progress * 100 / (progress + rest)) + "%"
    index++

$ -> loadData()

$ ->
  $("a").click ->
    myClass = this.className
    id = $(".message_class").attr("id")
    grade = 0
    if myClass.indexOf("danger") isnt -1
      grade = 1
    else if myClass.indexOf("warning") isnt -1
      grade = 2
    else if myClass.indexOf("default") isnt -1
      grade = 3
    else if myClass.indexOf("info") isnt -1
      grade = 4
    else
      grade = 5
    update()
    $.get "/grade?id=" + id + "&grade=" + grade



