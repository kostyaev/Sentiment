
var data = null;
var chart = null;
var options = null;

var tweetTime = -1.0;

google.load('visualization', '1', {packages:['gauge']});

google.setOnLoadCallback(drawChart);



function drawChart() {

    data = google.visualization.arrayToDataTable([
        ['Label', 'Value'],
        ['Твиты/сек.', 0]
    ]);

    options = {
    max : 60,
    redFrom: 40, redTo: 60,
    yellowFrom:20, yellowTo: 40,
    minorTicks: 5
    };

chart = new google.visualization.Gauge(document.getElementById('speed_chart_div'));
chart.draw(data, options);

}

function updateSpeed(x) {
    data.setCell(0,1,parseInt(x));
    chart.draw(data, options);
    }


