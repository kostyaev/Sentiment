var geoData = null;
var geoChart = null;
var geoOptions = null;

google.load('visualization', '1', {'packages': ['geochart']});
google.setOnLoadCallback(drawRegionsMap);

function drawRegionsMap() {
    geoData = google.visualization.arrayToDataTable([
        ['Country', 'Popularity'],
        ['Germany', 200],
        ['United States', 300],
        ['Brazil', 400],
        ['CA', 700],
        ['France', 600],
        ['RU', 700]
    ]);

    geoOptions = {
        width: 556,
        height: 347,
        colors: ['green', 'red']

    }

    geoChart = new google.visualization.GeoChart(document.getElementById('geo_chart_div'));
    geoChart.draw(geoData, geoOptions);

}