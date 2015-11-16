$(function() {

    var sample1 =[];

    var donutData = [];

    sample1 = [{party:"Republican",Negative:46541,Neutral:101625,Positive:12081},{party:"Democratic",Negative:15343,Neutral:38393,Positive:4219}];

    //$.ajax({
    //    url: "http://uselectionsui.mybluemix.net/api/mongo/bydate"
    //}).then(function(response) {
    //
    //
    //    console.log(response);
    //    donutData = response;
    //
    //    console.log('donut message');
    //    console.log(donutData);



       // donutData = [{label:"Republican", value:20},{label:"Democratic", value:60},{label:"Rulers", value:20}];


    //var sample = [{
    //    period: 'Nov-13 Q1',
    //    iphone: 2666,
    //    ipad: null,
    //    itouch: 2647
    //}, {
    //    period: 'Nov-13 Q2',
    //    iphone: 2778,
    //    ipad: 2294,
    //    itouch: 2441
    //}, {
    //    period: 'Nov-13 Q3',
    //    iphone: 4912,
    //    ipad: 1969,
    //    itouch: 2501
    //}, {
    //    period: 'Nov-13 Q4',
    //    iphone: 3767,
    //    ipad: 3597,
    //    itouch: 5689
    //}, {
    //    period: 'Nov-14 Q1',
    //    iphone: 6810,
    //    ipad: 1914,
    //    itouch: 2293
    //}, {
    //    period: 'Nov-14 Q2',
    //    iphone: 5670,
    //    ipad: 4293,
    //    itouch: 1881
    //}, {
    //    period: 'Nov-14 Q3',
    //    iphone: 4820,
    //    ipad: 3795,
    //    itouch: 1588
    //}, {
    //    period: 'Nov-14 Q4',
    //    iphone: 15073,
    //    ipad: 5967,
    //    itouch: 5175
    //}, {
    //    period: 'Nov-15 Q1',
    //    iphone: 10687,
    //    ipad: 4460,
    //    itouch: 2028
    //}, {
    //    period: 'Nov-15 Q2',
    //    iphone: 8432,
    //    ipad: 5713,
    //    itouch: 1791
    //}];

    //var sample1 = [{party:'Republican',Negative:35,Neutral:76,positive:86},{party:'Democratic',Negative:35,Neutral:76,positive:86}]


    var sample = [{
        period: 'Nov-13 Q1',
        Republican: 15431,
        Democratic: 1846
    }, {
        period: 'Nov-13 Q2',
        Republican: 21436,
        Democratic: 3456
    }, {
        period: 'Nov-13 Q3',
        Republican: 30436,
        Democratic: 4156
    }, {
        period: 'Nov-13 Q4',
        Republican: 30730,
        Democratic: 5948
    }, {
        period: 'Nov-14 Q1',
        Republican: 13546,
        Democratic: 9564
    }, {
        period: 'Nov-14 Q2',
        Republican: 15465,
        Democratic: 10483
    }, {
        period: 'Nov-14 Q3',
        Republican: 14545,
        Democratic: 11842
    }, {
        period: 'Nov-14 Q4',
        Republican: 16314,
        Democratic: 10660
    }];
    //}, {
    //    period: 'Nov-15 Q1',
    //    Republican: 2666,
    //    Democratic: null
    //}, {
    //    period: 'Nov-15 Q2',
    //    Republican: 2666,
    //    Democratic: null
    //}];



    console.log('outside method');
    console.log(donutData);

    //Morris.Area({
    //    element: 'morris-area-chart',
    //    data:sample ,
    //    xkey: 'period',
    //    ykeys: ['Republican', 'Democratic'],
    //    labels: ['Republican', 'Democratic'],
    //    pointSize: 2,
    //    hideHover: 'auto',
    //    resize: true
    //});

    Morris.Donut({
        element: 'morris-donut-chart',
    //    data:donutData
        data: [{
            label: "Republicans",
            value: 73
        }, {
            label: "Democratic",
            value: 27
        }]
    // , {
    //        label: "Mail-Order Sales",
    //        value: 20
    //    }],
    //    resize: true
    });

    Morris.Bar({
        element: 'morris-bar-chart',
        data: sample1,
        xkey: 'party',
        ykeys: ['Negative', 'Neutral','Positive'],
        labels: ['Negative', 'Neutral','Positive'],
        hideHover: 'auto',
        resize: true
    });

    });

//});
