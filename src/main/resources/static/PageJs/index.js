$(function () {
    //pos loading
    $.ajax({
        url:'/position/infoSelect',
        type:'GET',
        success:function(data){
            if(data.success){
                let posHTML='<div class="layui-form-item"><div class="layui-input-inline"><select name="position-select"><option>请选择职位</option>';
                $.each(data.hotWords,function(index,item){
                    posHTML+='<option>'+item.hotWord+'</option>';
                });
                posHTML+='</select></div><div class="layui-input-inline"><select name="city"><option value=""></option><option value="北京">北京</option><option value="上海">上海</option><option value="广州">广州</option><option value="深圳">深圳</option><option value="杭州">杭州</option><option value="天津">天津</option><option value="西安">西安</option><option value="苏州">苏州</option><option value="武汉">武汉</option><option value="厦门">厦门</option><option value="长沙">长沙</option><option value="成都">成都</option><option value="郑州">郑州</option><option value="重庆">重庆</option><option value="福州">福州</option><option value="贵阳">贵阳</option><option value="桂林">桂林</option><option value="佛山">佛山</option><option value="漳州">漳州</option></select></div><input id="receive" type="submit" class="layui-btn layui-btn-normal" value="获取"/></div>';
                $('#info-select').html(posHTML);
            }
        }
    });
    //注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function(){
        let element = layui.element;
    });
    let layer =null;
    layui.use('layer', function(){
        layer= layui.layer;
    });
    $(document).on('click','#display',function () {$('.container-distribute').hide();$('.container-online').hide();$('.container-map').hide();$('.container-word').hide();$('.container-exprirence').hide();$('.container-positions').show();});$(document).on('click','#online',function () { $('.container-positions').hide();$('.container-distribute').hide();$('.container-map').hide();$('.container-word').hide();$('.container-exprirence').hide();$('.container-online').show();});$(document).on('click','#distribute',function () {$('.container-positions').hide();$('.container-online').hide();$('.container-map').hide();$('.container-word').hide(); $('.container-exprirence').hide();$('.container-distribute').show();});$(document).on('click','#map',function () {$('.container-positions').hide();$('.container-online').hide(); $('.container-distribute').hide();$('.container-word').hide();$('.container-exprirence').hide();$('.container-map').show();});$(document).on('click','#word',function () {$('.container-positions').hide();$('.container-online').hide();$('.container-distribute').hide();$('.container-map').hide();$('.container-exprirence').hide();$('.container-word').show();});$(document).on('click','#exprirence',function () {$('.container-positions').hide();$('.container-online').hide();$('.container-distribute').hide();$('.container-map').hide();$('.container-word').hide(); $('.container-exprirence').show();});
    let page;
    var counts;
    $.ajax({
        url:'/position/getCount/kill'+'/you',
        async:false,
        cache:false,
        success:function (count) {
            if (count>0){
                counts=count;
            }
            $('.layui-badge').html(counts);
        }
    });
    layui.use('laypage',function () {
        page=layui.laypage;
        page.render({
            elem:'pos-page',
            limit:5,
            theme:'#c00',
            limits:[5, 10, 15, 20, 25],
            layout:['prev', 'page', 'next','limit'],
            curr:1,
            count:counts,
            jump:function (data,first) {
                //当刷新页面是第一页时他会为true
                if (first){
                    $.ajax({
                        url:'/position/getPosition?pageNum='+data.curr+'&pageSize='+data.limit+'&keyWord=&city=',
                        contentType:'text/html;charset=UTF-8',
                        success:function (data) {
                            let posHtml = '';
                            $.each(data,function (index,item) {

                                posHtml+='<div class="layui-col-md12"><span class="iconfont icon-tag" style="position: relative; top: 16px; right: 9px;"></span><i class="layui-icon layui-icon-time" style="float: right; position: relative; top: 18px;"></i><div class="layui-card"><div class="layui-card-header">岗位：<span class="title">' +
                                    item.positionTitle+'</span><time>' +
                                    item.positionWorkTime.slice(0,10)+'</time></div><div class="layui-card-body"><p>工资：<span class="salary">' +
                                    item.positionSalaryRange+'</span></p><p>规模：<span class="scale">' +
                                    item.positionCompanySIZE+'</span></p><p>成立日期：<span class="build-date">' +
                                    item.positionBuildTime+'</span></p><p>相关需求：<span class="requirement">' +
                                    item.positionRequire+'</span></p><p><a class="link" href="' +
                                    item.positionLink+'" style="float:right;position:relative;bottom: 14px;"><span class="iconfont icon-watch"></span>投递简历,在线沟通</a></p></div></div></div>';
                            });
                            $('.layui-row.layui-col-space15').html(posHtml);
                        }
                    });
                    return;
                }
                $.ajax({
                    url:'/position/getPosition?pageNum='+data.curr+'&pageSize='+data.limit,
                    contentType:'text/html;charset=UTF-8',
                    success:function (data) {
                        let posHtml = '';
                        $.each(data,function (index,item) {
                            posHtml+='<div class="layui-col-md12"><span class="iconfont icon-tag" style="position: relative; top: 16px; right: 9px;"></span><i class="layui-icon layui-icon-time" style="float: right; position: relative; top: 18px;"></i><div class="layui-card"><div class="layui-card-header">岗位：<span class="title">' +
                                item.positionTitle+'</span><time>' +
                                item.positionWorkTime.slice(0,10)+'</time></div><div class="layui-card-body"><p>工资：<span class="salary">' +
                                item.positionSalaryRange+'</span></p><p>规模：<span class="scale">' +
                                item.positionCompanySIZE+'</span></p><p>成立日期：<span class="build-date">' +
                                item.positionBuildTime+'</span></p><p>相关需求：<span class="requirement">' +
                                item.positionRequire+'</span></p><p><a class="link" href="' +
                                item.positionLink+'" style="float:right;position:relative;bottom: 14px;"><span class="iconfont icon-watch"></span>投递简历,在线沟通</a></p></div></div></div>';
                        });
                        $('.layui-row.layui-col-space15').html(posHtml);
                    }
                });
            }
        })
    });
    //获取职位筛选和城市内容
    $(document).on('click','#receive',function () {
        //获取职位和城市内容
        let pos=$('select[name=position-select] option:selected').html();
        let city=$('select[name=city] option:selected').html();
        $.ajax({
            url:'/position/getCount/'+pos+'/'+city,
            async:false,
            cache:false,
            success:function (count) {
                if (count>0){
                    counts=count;
                }
                $('.layui-badge').html(counts);
            }
        });
        page.render({
            elem:'pos-page',
            limit:5,
            theme:'#c00',
            limits:[5, 10, 15, 20, 25],
            layout:['prev', 'page', 'next','limit'],
            curr:1,
            count:counts,
            jump:function (data,first) {
                //当刷新页面是第一页时他会为true
                if (first){
                    $.ajax({
                        url:'/position/getPosition?pageNum='+data.curr+'&pageSize='+data.limit+'&keyWord='+pos+'&city='+city,
                        contentType:'text/html;charset=UTF-8',
                        success:function (data) {
                            let posHtml = '';
                            $.each(data,function (index,item) {
                                posHtml+='<div class="layui-col-md12"><span class="iconfont icon-tag" style="position: relative; top: 16px; right: 9px;"></span><i class="layui-icon layui-icon-time" style="float: right; position: relative; top: 18px;"></i><div class="layui-card"><div class="layui-card-header">岗位：<span class="title">' +
                                    item.positionTitle+'</span><time>' +
                                    item.positionWorkTime.slice(0,10)+'</time></div><div class="layui-card-body"><p>工资：<span class="salary">' +
                                    item.positionSalaryRange+'</span></p><p>规模：<span class="scale">' +
                                    item.positionCompanySIZE+'</span></p><p>成立日期：<span class="build-date">' +
                                    item.positionBuildTime+'</span></p><p>相关需求：<span class="requirement">' +
                                    item.positionRequire+'</span></p><p><a class="link" href="' +
                                    item.positionLink+'" style="float:right;position:relative;bottom: 14px;"><span class="iconfont icon-watch"></span>投递简历,在线沟通</a></p></div></div></div>';
                            });
                            $('.layui-row.layui-col-space15').html(posHtml);
                        }
                    });
                    return;
                }
                $.ajax({
                    url:'/position/getPosition?pageNum='+data.curr+'&pageSize='+data.limit+'&keyWord='+pos+'&city='+city,
                    contentType:'text/html;charset=UTF-8',
                    success:function (data) {
                        let posHtml = '';
                        $.each(data,function (index,item) {
                            posHtml+='<div class="layui-col-md12"><span class="iconfont icon-tag" style="position: relative; top: 16px; right: 9px;"></span><i class="layui-icon layui-icon-time" style="float: right; position: relative; top: 18px;"></i><div class="layui-card"><div class="layui-card-header">岗位：<span class="title">' +
                                item.positionTitle+'</span><time>' +
                                item.positionWorkTime.slice(0,10)+'</time></div><div class="layui-card-body"><p>工资：<span class="salary">' +
                                item.positionSalaryRange+'</span></p><p>规模：<span class="scale">' +
                                item.positionCompanySIZE+'</span></p><p>成立日期：<span class="build-date">' +
                                item.positionBuildTime+'</span></p><p>相关需求：<span class="requirement">' +
                                item.positionRequire+'</span></p><p><a class="link" href="' +
                                item.positionLink+'" style="float:right;position:relative;bottom: 14px;"><span class="iconfont icon-watch"></span>投递简历,在线沟通</a></p></div></div></div>';
                        });
                        $('.layui-row.layui-col-space15').html(posHtml);
                    }
                });
            }
        });
        //动态生成公司标注
        $.ajax({
            url:'/Map/getCompany/'+pos+'/'+city,
            type:'GET',
            contentType:'text/html; charset=UTF-8',
            success:function(data){
                if (data.success){
                    //实现地图多标注的问题
                    var map = new BMap.Map('container');
                    map.centerAndZoom(city, 15);
                    map.enableScrollWheelZoom(true);
                    for (let i=0;i<data.points.length;i++){
                        let marker=new BMap.Marker(new BMap.Point(data.points[i].x, data.points[i].y),{title:data.points[i].branch});
                        map.addOverlay(marker);
                    }
                }else {
                    layer.msg(data.errMsg);
                }
            }
        })
    });
    //distribute
    let myChart = echarts.init(document.getElementById('salary-area'));
    let option;
    $(document).on('click','#search-submit',function () {
        $.ajax({
            url:'/position/getSalaryS',
            data:$("#data-search").serialize(),
            async: false,
            type:"POST",
            success:function (data) {
                if (data.success){
                    //拼接option参数
                    let City=[];
                    let salary=[];
                    $.each(data.salaryS,function (index,item) {
                        City.push(item.city);
                        salary.push(item.salary)
                    });
                    option = {title: {text: '实时工资分布', textStyle:{color: 'white' },subtext: '数据来自后台'}, tooltip: {trigger: 'axis', axisPointer: {type: 'shadow'}}, legend: {data: ['2021']}, grid: {left: '3%',right: '4%', bottom: '3%', containLabel: true}, xAxis: {type: 'value', boundaryGap: [0, 0.01]},
                        yAxis: {
                            type: 'category',
                            data: City
                        },
                        series: [
                            {
                                name: '2021年',
                                type: 'bar',
                                data: salary
                            }
                        ]
                    };
                    myChart.setOption(option);
                    $('#salary-area').show();
                    $('#data-search > input').val('');
                }else {
                    layer.msg(data.errMsg);
                }
            }
        })
    });
    //online-spider
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(addpos)', function(data){
            layer.msg("请稍等片刻,正在进行数据爬取", {icon: 6});
            $.ajax({
                url:'/position/addPos',
                type:'POST',
                data:$('#form-submit').serialize(),
                success:function (data) {
                    if(data.success){
                        layer.msg(data.count);
                        setInterval(function () {
                            location.href=data.url;
                        },1000);
                    }else {
                        layer.msg(data.errMsg);
                        if (data.count!=null){
                            layer.msg(data.count);
                        }
                        setInterval(function () {
                            location.href=data.url;
                        },1000);
                    }
                }
            });
            console.log($('#form-submit').serialize());
            return false;
        });
    });
    //hot-word
    var data = [
        ['Java', 90],
        ['PHP', 26],
        ['MySQL', 80],
        ['Python', 77],
        ['Oracle', 96],
        ['SQL Server', 29],
        ['Spring	', 57],
        ['Spring Data', 42],
        ['JDBC', 19],
        ['Tomcat', 39],
        ['Nginx', 66],
        ['DB2', 24],
        ['Spring MVC', 58],
        ['C', 6],
        ['C++', 70],
        ['Limbo ', 45],
        ['S2', 59],
        ['Amiga Basic', 92],
        ['COBOL', 82],
        ['Unicon ', 10],
        ['FORTRAN II ', 63],
        ['Revolution ', 14],
        ['Haxe ', 45],
        ['Html', 57],
        ['Css', 8],
        ['JavaScript', 68],
        ['Windows PowerShell', 38],
        ['C#', 51],
        ['Spring AMQP', 80],
        ['SpringBoot', 90],
        ['SpringCloud', 84],
        ['Spring JDBC', 13],
        ['MyBatis', 89],
        ['Spring WebService', 35],
        ['SpringKAFKA', 48],
        ['Java ', 91],
        ['PHP ', 28],
        ['MySQL ', 85],
        ['Python ', 19],
        ['Oracle ', 63],
        ['SQL Server ', 4],
        ['Spring	 ', 72],
        ['Spring Data ', 28],
        ['JDBC ', 28],
        ['Tomcat ', 95],
        ['Nginx ', 73],
        ['DB2 ', 68],
        ['Spring MVC ', 18],
        ['C ', 19],
        ['C++ ', 75],
        ['Limbo  ', 63],
        ['S2 ', 33],
        ['Amiga Basic ', 15],
        ['COBOL ', 41],
        ['Unicon  ', 82],
        ['FORTRAN II  ', 80],
        ['Revolution  ', 91],
        ['Haxe  ', 17],
        ['Html ', 45],
        ['Css ', 90],
        ['JavaScript ', 38],
        ['Windows PowerShell ', 94],
        ['C# ', 45],
        ['Spring AMQP ', 71],
        ['SpringBoot ', 17],
        ['SpringCloud ', 31],
        ['Spring JDBC ', 10],
        ['MyBatis ', 44],
        ['Spring WebService ', 49],
        ['SpringKAFKA ', 32]
    ];

    var string_ = "";
    for(var i = 0; i < data.length; i++) {
        var string_f = data[i][0];
        var string_n = data[i][1];
        string_ += "{text: '" + string_f + "', weight: '" + string_n + "',html: {'class': 'span_list',onmouseover:'on_mouseover(this,event)',onmouseout:'on_mouseout()'}},";
    }

    var string_list = string_;
    var word_list = eval("[" + string_list + "]");

    $(function() {
        $("#word_div").jQCloud(word_list, {});
    });
    //help说明
    $(document).on('click','.help',function(){
        //示范一个公告层
        layer.open({
            type: 1
            ,title: false //不显示标题栏
            ,closeBtn: false
            ,area: '500px;'
            ,shade: 0.8
            ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
            ,btn: ['已了解']
            ,btnAlign: 'c'
            ,moveType: 1 //拖拽模式，0或者1
            ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;"><h3>网站使用说明</h3><br>1.本网站是纯个人开发,用需要进行职位查找的用户进行职位查找<br>2.本网站会对使用该网站用户检测,若发现恶心破坏该网站运行行为的将进行Ip封锁处理！<br>-----------------------------------------------------------------------------------<br><h3>网站使用</h3><br>1.职位默认展示是java相关的各个城市岗位;若该岗位非用户心仪岗位,用户可进行检索;注意！！检索中职位选择和城市必须存在,否则会检索失败,无职位信息返回<br>2.在线爬取,本职位现在提供在线爬取功能,可对自己感兴趣的职位进行岗位搜索爬取,最后方便查看<br>3.用户可以对岗位关键进行查看工资分布,但请注意,若需要查看工资分布请在同一关键词下查询多个城市岗位信息方可进行工资分布统计<br>4.公司分布位置是在职位展示信息搜索之前的,若未进行检索,则分布位置不存在<br>5.热词统计是关于计算机行业技术记录<br>6.简单介绍了网站的开发过程</div>'
        });
    })
    layui.use('upload',function () {
        //指定允许上传的文件类型
        var upload = layui.upload;
        upload.render({
            elem: '#test3'
            ,url: '/upload/cookie' //改成您自己的上传接口
            ,accept: 'file' //普通文件
            ,done: function(res){
                if (res.success){
                    layer.msg(res.count);
                }else {
                    layer.msg(res.errMsg,{icon:5});
                }
            }
        });
    })
});