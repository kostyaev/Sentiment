var pieData = null;
var pieChart = null;
var pieOptions = null;


google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawPieChart);


function drawPieChart() {
    pieData = google.visualization.arrayToDataTable([
        ['Sentiment', 'Count'],
        ['Положительная',     1],
        ['Нейтальная',      1],
        ['Негативная',  1]
    ]);

    pieOptions = {
        title: 'Диаграмма тональности текстов',
        slices: {
            0: { color: 'green' },
            1: { color: 'grey' },
            2: { color: 'red' }
        }
    };



    pieChart = new google.visualization.PieChart(document.getElementById('pie_chart_div'));
    pieChart.draw(pieData, pieOptions);
}


function updatePieChart(grade) {
    if (grade == 0)
        pieData.setValue(2,1, pieData.getValue(2,1) + 1 );
    else if (grade == 1)
        pieData.setValue(1,1, pieData.getValue(1,1) + 1 );
    else
        pieData.setValue(0,1, pieData.getValue(0,1) + 1 );

    pieChart.draw(pieData, pieOptions);
}