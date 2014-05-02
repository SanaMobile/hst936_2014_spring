<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="MIT SANA Clean Hive">
    <meta name="author" content="Bondili Rohan Singh">
    <link rel="shortcut icon" href="assets/icons/favicon.ico">
    <title>Clean Hive| Contact Us</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/contactus.css">
  
  </head>
<body>

<div class="jumbotron jumbotron-sm">
    <div class="container">
        <div class="row">
            <div class="col-sm-12 col-lg-12">
                <h1 class="h1">Contact Us <small>Feel Free to contact us</small></h1>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-8">
            <div class="well well-sm">
                <form>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <p id = "header" for="name">
                                Name</p>
                            <input type="text" class="form-control" id="name" placeholder="Enter name" required="required" />
                        </div>
                        <div class="form-group">
                            <p id="header" for="email">
                                Email Address</p>
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span>
                                </span>
                                <input type="email" class="form-control" id="email" placeholder="Enter email" required="required" /></div>
                        </div>
                        <div class="form-group">
                            <p id ="header" for="subject">
                                Subject</p>
                            <select id="subject" name="subject" class="form-control" required="required">
                                <option value="na" selected="">Choose One:</option>
                                <option value="product">Feedback</option>
                                <option value="service">Job Application</option>
                                <option value="suggestions">Suggestions</option>
                                <option value="product">General Query</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <p id ="header" for="name">
                                Message</p>
                            <textarea name="message" id="message" class="form-control" rows="9" cols="25" required="required"
                                placeholder="Message"></textarea>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <button type="submit" class="btn btn-primary pull-right" id="btnContactUs">
                            Send Message</button>
                    </div>
                </div>
                </form>
            </div>
        </div>
        <div class="col-md-4">
            <form>
            <legend id ="info"><span class="glyphicon glyphicon-globe"></span> Our office</legend>
            <address>
                <p id = "info">Clean Hive<br></p><p id ="header">
                Addiction Recovery,MIT SANA<br>
                <br>
                <span class="glyphicon glyphicon-earphone"></span>
                 (+001) 932-1239-533.</p>
            <address>
                <!-- <strong>Full Name</strong><br>
                <a href="mailto:#">first.last@example.com</a> -->
            </address>
            </form>
        </div>
    </div>
</div>
</body>
</html>