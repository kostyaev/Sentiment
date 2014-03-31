message = null
progress = null
rest = null
messageWithId = null
size = 0

loadData = () -> $.post "/data", (jsdata) ->
  message = jsdata.message
  progress = jsdata.progress
  rest = jsdata.rest
  size = message.length
  update()

update = () ->
    messageWithId = message
    $(".message_class").html messageWithId.message
    $(".message_class").attr "id", messageWithId.id
    $("#countDone").html progress
    $("#countTodo").html rest
    $("#result-progress").css "width", (progress * 100 / (progress + rest)) + "%"

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
    $.get "/grade?id=" + id + "&grade=" + grade, () -> loadData()



