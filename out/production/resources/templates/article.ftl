<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <title>Article | ${title}</title>
</head>
<body class="container">
<h1 class="text-center">Article: ${title}</h1>

<p>${text}</p>

<a href="/" class="m-3">Back</a>

<form method="post" action="/comment" class="m-5">
    <div class="form-group">
        <textarea class="form-control" id="com_content_field" rows="3" name="com_content" placeholder="Your comment ..." required></textarea>
        <input type="hidden" value="${id}" name="article_id">
    </div>
    <button type="submit" class="btn btn-primary">Ajouter</button>
</form>

<ul class="list-group">
    <#if comments?has_content>
        <#list comments as x>
            <div class="jumbotron">
                <li class="list-group-item"> ${comments[x?index].content}</li>
            </div>
        </#list>
    <#else>
        <div class="alert alert-warning text-center" role="alert">
            No comments for the moment !
        </div>
    </#if>
</ul>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>