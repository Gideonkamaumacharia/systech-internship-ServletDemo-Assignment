
<style>
    body { font-family: 'Inter', sans-serif; background: #f0f2f5; display: flex; justify-content: center; padding-top: 50px; }

    .form-container { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); width: 100%; max-width: 400px; }

    label { display: block; margin-top: 15px; font-weight: 600; color: #555; font-size: 14px; }

    input { width: 100%; padding: 12px; margin-top: 5px; border: 1px solid #ddd; border-radius: 6px; box-sizing: border-box; }

    input:focus { border-color: #2a5298; outline: none; }

    button { width: 100%; background: #1e3c72; color: white; border: none; padding: 14px; margin-top: 25px; cursor: pointer; border-radius: 6px; font-weight: bold; }

    button:hover { background: #2a5298; }
</style>


<div class="form-container">
    <h1>Register New User</h1>
    <form action="user" method="POST">
        <label>ID</label>
        <input type="number" name="id" placeholder="Enter user ID" required>

        <label>User Name </label>
        <input type="text" name="username" placeholder="Enter user name" required>

        <label>Password</label>
        <input type="text" name="password" placeholder="Enter password" required>

        <label>Role</label>
        <input type="text" name="role" placeholder="Role" required>


        <button type="submit">Sign up</button>
        <div class="navigation">
            <a href="user_list" class="nav-link">View Users</a>
        </div>
    </form>
</div>