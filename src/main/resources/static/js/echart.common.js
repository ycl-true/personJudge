function rankAlert(msg){
    layer.alert(msg, {
        skin: 'layui-layer-lan',
        closeBtn: 0,
        shift: 1 //动画类型
    });
    // 不显示之前的图像
    $("#rank-show-parent").empty();
    $("#rank-show-parent").append('<div id="rank-show">\n' +
        '                                <div class="middle-box text-center animated fadeInRightBig" style="margin-bottom: 184px">\n' +
        '                                    <h3 class="font-bold">请先选择查询选项~</h3>\n' +
        '                                    <div class="error-desc">\n' +
        '                                        (≧∇≦)ﾉ 课程必选哦！\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                            </div>');
    $("#rank-show").css("height","");
}
function setRankHtml(objArray) {
    if(objArray.length == 0){
        rankAlert('╮(╯▽╰)╭ 没有教师教这门课~');
        return;
    }
    if(objArray.length == 1 && objArray[0].rankScope == -1){
        rankAlert('╮(╯▽╰)╭ 该课程尚未有人评价~');
        return;
    }
    var nameArray = new Array();
    var scopeArray = new Array();
    for(var i in objArray){
        nameArray.push(objArray[i].teacherName);
        scopeArray.push(objArray[i].rankScope);
    }
    // 计算背景长度？
    $("#rank-show").css("height","410px");
    // $("#rank-show").removeAttr("class").attr("class", "");
    $("#rank-show").addClass("animated fadeIn");
    var e = echarts.init(document.getElementById("rank-show")),
    option = {
        title: {
            text: "教师排名",
            subtext: $("#sele").children('option:selected').text()
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            position: 'top',
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: nameArray
        },
        series: [
            {
                name: $("#sele").children('option:selected').text(),
                type: 'bar',
                data: scopeArray,
                label: {
                    normal: {
                        show: true,//显示数字
                        position: 'right',
                        offset: [5, -2],
                        textStyle: {
                            color: '#F68300',
                            fontSize: 13
                        }
                    }
                },
                barWidth: 20,//柱子宽度
                itemStyle: {
                    emphasis: {
                        barBorderRadius: 7
                    },
                    normal: {
                        barBorderRadius: 7,
                        // 渐变
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 1, 0,
                            [
                                {offset: 0, color: '#3977E6'},
                                {offset: 1, color: '#37BBF8'}
                            ]
                        )
                    }
                },
            }
        ]
    };
    e.setOption(option, true), $(window).resize(e.resize);
}
