<?php
    $servername = "localhost";
    $username = "root";
    $password = "";
    $database = "eBook_MetaData";

    // Create connection
    $conn = mysqli_connect($servername, $username, $password, $database);
    // Check connection
    if (!$conn) {
        die("Connection failed: " . mysqli_connect_error());
    }

    // sql to create table
    $sql = "CREATE TABLE `eBook_MetaData` (
        `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `creator` varchar(1000) NOT NULL,
        `title` varchar(1000) NOT NULL,
        `type` varchar(1000) NOT NULL,
        `identifier` varchar(1000) NOT NULL,
        `date` DATE NOT NULL,
        `language` varchar(1000) NOT NULL,
        `description` text NOT NULL
    )";

    if (mysqli_query($conn, $sql)) {
        echo "Table eBook_MetaData created successfully";
    } else {
        echo "Error creating table: " . mysqli_error($conn);
    }

    mysqli_close($conn);
?>