<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Update Vehicle | Elite Showroom</title>

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

    <style>

        *{
            margin:0;
            padding:0;
            box-sizing:border-box;
        }

        body{
            font-family:'Inter', sans-serif;
            background: linear-gradient(135deg, #0f172a, #1e3c72);
            min-height:100vh;

            display:flex;
            justify-content:center;
            align-items:center;

            padding:20px;
            color:#e2e8f0;
        }

        .form-container{

            width:100%;
            max-width:500px;

            background: rgba(15, 23, 42, 0.85);
            backdrop-filter: blur(12px);

            border:1px solid rgba(255,255,255,0.08);
            border-radius:20px;

            padding:35px;

            box-shadow:0 20px 50px rgba(0,0,0,0.45);
        }

        h1{
            color:#ffffff;
            margin-bottom:25px;
            font-size:1.8rem;
            text-align:center;
        }

        .form-group{
            margin-bottom:18px;
        }

        label{
            display:block;
            margin-bottom:8px;
            font-size:0.9rem;
            font-weight:600;
            color:#cbd5e1;
        }

        input{
            width:100%;
            padding:11px 14px;

            border-radius:12px;
            border:1px solid rgba(255,255,255,0.08);

            background:#1e293b;
            color:#ffffff;

            outline:none;

            transition:0.3s ease;
        }

        input:focus{
            border-color:#38bdf8;
            box-shadow:0 0 0 4px rgba(56,189,248,0.15);
        }

        button{

            width:100%;
            margin-top:10px;

            padding:12px;

            border:none;
            border-radius:12px;

            background: linear-gradient(to right, #38bdf8, #6366f1);

            color:white;
            font-weight:600;

            cursor:pointer;

            transition:0.3s ease;
        }

        button:hover{
            transform:translateY(-2px);
            box-shadow:0 10px 25px rgba(56,189,248,0.25);
        }

        .navigation{
            margin-top:20px;
            text-align:center;
        }

        .navigation a{
            color:#94a3b8;
            text-decoration:none;
            font-size:0.9rem;
            transition:0.3s ease;
        }

        .navigation a:hover{
            color:#ffffff;
        }

    </style>

</head>

<body>

<div class="form-container">

    <h1>Update Showroom</h1>

    <form action="updateShowroom" method="post">

        <input type="hidden"
               name="id"
               value="${showroom.id}" />

        <div class="form-group">
            <label>Showroom Name</label>

            <input type="text"
                   name="locationName"
                   value="${showroom.locationName}"
                   placeholder="Enter showroom manager"
                   required />
        </div>


        <div class="form-group">
            <label>Showroom Capacity</label>

            <input type="number"
                   name="capacity"
                   value="${showroom.capacity}"
                   placeholder="Enter showroom capacity"
                   required />
        </div>

        <button type="submit">
            Update Showroom
        </button>

    </form>

    <div class="navigation">
        <a href="showroom_list">&larr; Back to Showroom</a>
    </div>

</div>

</body>
</html>