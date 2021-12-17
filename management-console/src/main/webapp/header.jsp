<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        function deleteApp(appId) {
            $.ajax({
                url: '/management-console/application?appid=' + appId,
                type: 'DELETE',
                success: function(result) {
                    console.log(result);
                    window.location.href = '/management-console/application';
                }
            });
        };
        function deleteMetric(metricId) {
            $.ajax({
                url: '/management-console/metric?metricid=' + metricId,
                type: 'DELETE',
                success: function(result) {
                    console.log(result);
                    window.location.href = '/management-console/metric';
                }
            });
        };
        function clearDashboards() {
            $.ajax({
                url: '/management-console/dashboard',
                type: 'DELETE',
                success: function(result) {
                    console.log(result);
                    window.location.href = '/management-console/dashboard';
                }
            });
        };
    </script>
</head>

<body>

    <nav class="navbar navbar-expand-sm bg-primary navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="/management-console/">Management Console</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/management-console/application">Applications</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/management-console/metric">Metrics</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/management-console/dashboard">Dashboards</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>