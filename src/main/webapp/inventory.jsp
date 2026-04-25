
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
    <h1>Register New Vehicle</h1>
    <form action="inventory" method="POST">
        <label>Car Model</label>
        <input type="text" name="carModel" placeholder="e.g. Range Rover Sport" required>

        <label>Engine Specification</label>
        <input type="text" name="engineType" placeholder="e.g. 5.0L V8 Supercharged" required>

        <label>Year</label>
        <input type="number" name="year" min="1900" max="2030" placeholder="e.g. 2026" required>

        <label>Price</label>
        <input type="number" step="0.01" name="price" placeholder="e.g. 50000.00" required>

        <button type="submit">Add to Showroom</button>
        <div class="navigation">
            <a href="list" class="nav-link">View Registered Cars</a>
        </div>
    </form>
</div>