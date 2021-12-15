<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/signin.css">
    <script src="/resources/js/auto-height.js"></script>
</head>

<body class="text-center">

<main class="form-signin">
    <form method="post" action="/sign-up">
        <img class="mb-4" src="/resources/files/heart.png" alt="" width="72" height="57">
        <h1 class="h3 mb-3 fw-normal">Please sign up</h1>

        <div class="form-floating">
            <input name="email" type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
            <label for="floatingInput">Email address</label>
        </div>
        <div class="form-floating">
            <input name="password" type="password" class="form-control" id="floatingFirst" placeholder="password">
            <label for="floatingFirst">Password</label>
        </div>
        <div class="form-floating">
            <input name="firstName" type="text" class="form-control" id="floatingFirst" placeholder="first name">
            <label for="floatingFirst">First name</label>
        </div>
        <div class="form-floating">
            <input name="lastName" type="text" class="form-control" id="floatingLast" placeholder="last name">
            <label for="floatingLast">Last name</label>
        </div>

        <div class="form-floating">
            <textarea name="description" type="text" class="form-control auto-height" id="floatingLast" cols="40"
                      rows="5" oninput="auto_height(this)" placeholder="description"></textarea>
        </div>
        <div class="form-floating">
            <input name="age" type="number" class="form-control" id="floatingAge" placeholder="age" min="18" max="100">
            <label for="floatingAge">Age</label>
        </div>

        <select size="1" multiple name="gender">
            <option disabled>Выберите пол</option>
            <option value="мужчина">Мужчина</option>
            <option value="женщина">Женщина</option>
        </select>

        <button class="w-100 btn btn-lg btn-primary" type="submit">Sign up</button>
    </form>
    <#if error??>
        ${error.message}
    </#if>

</main>
</body>
</html>