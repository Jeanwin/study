<div id="echarts${id}" style="height:280px;width:1500px;border:0px solid #ccc;padding:6px;"></div>
<script src="/study/static/js/echarts/echarts.js"></script>
<script type="text/javascript">
	$(function() {
		require.config({
			paths : {
				echarts : '/study/static/js/echarts/dist'
			}
		});
		document.getElementById('echarts${id}').style.width = document.getElementById('active_chart_pie').style.width;
		require([ 'echarts', 'echarts/chart/pie', 'echarts/chart/funnel' ], function(ec) {
			var myChart = ec.init(document.getElementById('echarts${id}'));
			var labelFromatter = {
				normal : {
					borderWidth : 0,
					label : {
						formatter : function(params) {
							console.log("pie params", params);
							return Math.round(100 - params.percent) + '%'
						},
						textStyle : {
							baseline : 'top'
						}
					}
				},
			};
			var labelTop = {
				normal : {
					label : {
						show : true,
						position : 'center',
						formatter : '{b}',
						textStyle : {
							baseline : 'bottom'
						}
					},
					labelLine : {
						show : false
					}
				}
			};
			var labelBottom = {
				normal : {
					color : '#ccc',
					label : {
						show : true,
						position : 'center'
					},
					labelLine : {
						show : false
					}
				},
				emphasis : {
					color : 'rgba(0,0,0,0)'
				}
			};
			var radius = [ '50%', '65%' ];
			var data = ${data};
			console.log("bingtu", data);
			for (var i = 0; i < data.length; i++) {
				if (i == 0) {
					data[i].itemStyle = labelBottom;
				} else if (i == 1) {
					data[i].itemStyle = labelTop;
				} else {
				}
			}
			myChart.setOption({
				legend : {
					x : 'center',
					y : 'bottom',
					data : ${legend},
				},
				/*title : {
					text : '${functitle}',
					x : 'left'
				},*/
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : true,
							readOnly : false
						},
						magicType : {
							show : false,
							type : [ 'pie', 'funnel' ],
							option : {
								funnel : {
									width : '40%',
									height : '60%',
									itemStyle : {
										normal : {
											label : {
												formatter : function(params) {
													console.log("paramfunnel:", params);
													return Math.round(100 - params.percent) + '%\n'
												},
												textStyle : {
													baseline : 'middle'
												}
											}
										},
									},
									calculable : true,
									series : [

									{
										name : '评审进度',
										type : 'funnel',
										center : [ '50%', '50%' ],
										//radius : radius,
										//minAngle : 1,
										x : '0%',
										itemStyle : labelFromatter,
										data : data,
									}

									]
								}
							}
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				series : [

				{
					name : '评审进度',
					type : 'pie',
					center : [ '50%', '50%' ],
					radius : radius,
					minAngle : 1,
					x : '0%',
					itemStyle : labelFromatter,
					data : data,
				}

				]
			}).setTheme('macarons');
		})
	});
</script>
<div style="position:absolute;top:30px;left:30px;font-size:20px;color:rgb(223, 100, 78);">共计 : ${total}</div>
<div style="position:absolute;top:65px;left:30px;font-size:20px;color:green;">评审完成 : ${reviewed}</div>

