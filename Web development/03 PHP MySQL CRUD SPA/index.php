<!DOCTYPE html>
<html>
<head>
    <title>Lab 5 Assignment 3</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="assign3.css">
    <script src="assign3.js"></script>
</head>


<body>
  
    <?php
    session_start();

    $creatorErr = $titleErr = $typeErr = $identifierErr = $dateErr = $languageErr = $descriptionErr = "";
    $id = $creator = $title = $type = $identifier = $date = $language = $description = "";
    $dataAvailable = false;
    $edit_state = false;

    $servername = "localhost";
    $username = "root";
    $password = "";
    $database = "eBook_MetaData";

    //Part 1: Create Row Data Zone
    if (isset($_POST['create'])) {
        $dataAvailable = true;
        if (empty($_POST['creator'])) {
            $dataAvailable = false;
            $creatorErr = "creator is required";
        } else {
            $creator = test_input($_POST['creator']);
        }
        
        if (empty($_POST['title'])) {
            $dataAvailable= false;
            $titleErr = "title is required";
        } else {
            $title = test_input($_POST['title']);  
        }

        if (empty($_POST['type'])) {
            $dataAvailable= false;
            $typeErr = "type is required";
        } else {
            $type = test_input($_POST['type']);
        }

        if (empty($_POST['identifier'])) {
            $dataAvailable= false;
            $identifierErr = "identifier is required";
        } else {
            $identifier = test_input($_POST['identifier']);
        }

        if (empty($_POST['date'])) {
            $dataAvailable= false;
            $dateErr = "date is required";
        } else {
            $date = test_input($_POST['date']);
        }

        if (empty($_POST['language'])) {
            $dataAvailable= false;
            $languageErr = "language is required";
        } else {
            $language = test_input($_POST['language']);
        }

        if (empty($_POST['description'])) {
            $dataAvailable= false;
            $descriptionErr = "description is required";
        } else {
            $description = test_input($_POST['description']);
        }
    }

    function test_input($data) {
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }

    if ($dataAvailable == true){
        //connect to database
        $conn = new mysqli($servername, $username, $password, $database);
        if ($conn->connect_error) {
            die("Connection failed: " . mysqli_connect_error());
        } 

        $query = "SELECT COUNT(id) AS numrows FROM eBook_MetaData 
                    WHERE `creator` = '$creator' AND `title` = '$title' AND `type` = '$type' AND `identifier` = '$identifier' AND `date` = '$date' AND `language` = '$language' AND `description` = '$description'";
        
        $result = $conn->query($query);
        
        $row = $result->fetch_assoc();
        
        if ($row['numrows'] == 0) {
            $query = "INSERT INTO eBook_MetaData(`creator`, `title`, `type`, `identifier`, `date`, `language`, `description`) 
            VALUES (?, ?, ?, ?, ?, ?, ?)";
            if ($stmt = $conn->prepare($query)) {
                    $stmt->bind_param("sssssss", $creator, $title, $type, $identifier, $date, $language, $description);
                    $result = $stmt->execute();
                    $stmt->close();
                    if ($result == true) $_SESSION['msg'] = "One New Row Is Added";
            } else {
                echo $conn->error;
            }
        } else {
            $_SESSION['msg'] = "The Data Already Exists";
        }
        
        
        $creator = $title = $type = $identifier = $date = $language = $description = "";
        $conn->close();

        $dataAvailable = false;
    }

    // Part 3: Update Row Data
    if (isset($_POST['idEdit'])) {
        $id = $_POST['idEdit'];
        $conn = new mysqli($servername, $username, $password, $database);
        if ($conn->connect_error) {
            die("Connection failed: " . mysqli_connect_error());
        } 

        $query = "SELECT `creator`, `title`, `type`, `identifier`, `date`, `language`, `description` FROM eBook_MetaData WHERE `id` = $id";
        $result = $conn->query($query);
        
        if ($result->num_rows == 1) {
            $row = $result->fetch_assoc();
            $creator = $row['creator'];
            $title = $row['title'];
            $type = $row['type'];
            $identifier = $row['identifier'];
            $date = $row['date'];
            $language = $row['language'];
            $description = $row['description'];

        } else {
            $_SESSION['msg'] = "The Row $id Selected Does Not Exist In eBook_MetaData";
        }

        $conn->close();
        $edit_state = true;
    }

    if (isset($_POST['update'])) {
      
        $dataUpdate = true;
        $id = $_POST['id'];

        if (empty($_POST['creator'])) {
            $dataUpdate = false;
            $creatorErr = "creator is required";
        } else {
            $creator = test_input($_POST['creator']);
        }
        
        if (empty($_POST['title'])) {
            $dataUpdate= false;
            $titleErr = "title is required";
        } else {
            $title = test_input($_POST['title']);  
        }

        if (empty($_POST['type'])) {
            $dataUpdate= false;
            $typeErr = "type is required";
        } else {
            $type = test_input($_POST['type']);
        }

        if (empty($_POST['identifier'])) {
            $dataUpdate= false;
            $identifierErr = "identifier is required";
        } else {
            $identifier = test_input($_POST['identifier']);
        }

        if (empty($_POST['date'])) {
            $dataUpdate= false;
            $dateErr = "date is required";
        } else {
            $date = test_input($_POST['date']);
        }

        if (empty($_POST['language'])) {
            $dataUpdate= false;
            $languageErr = "language is required";
        } else {
            $language = test_input($_POST['language']);
        }

        if (empty($_POST['description'])) {
            $dataUpdate= false;
            $descriptionErr = "description is required";
        } else {
            $description = test_input($_POST['description']);
        }  

        if ($dataUpdate == true) {
            $conn = new mysqli($servername, $username, $password, $database);
            if ($conn->connect_error) {
                die("Connection failed: " . mysqli_connect_error());
            } 

            $query = "UPDATE eBook_MetaData 
            SET  `creator` = ?, `title` = ?, `type` = ?, 
                `identifier` = ?, `date` = ?, `language` = ?, `description` = ?
            WHERE `id` = ?";
        
            if ($stmt = $conn->prepare($query)) {
                $stmt->bind_param("sssssssi", $creator, $title, $type, $identifier, $date, $language, $description, $id);
                $result = $stmt->execute();
                $stmt->close();
                if ($result == true) { 
                    $_SESSION['msg'] = "Row id $id is Updated";
                } else {
                    $_SESSION['msg'] = "The id $id For Updating Does Not Exist";
                }
            } else {
                echo $conn->error;
            }

            $conn->close();
            $edit_state = false;
            $dataUpdate = false;
        }
        
    }

    //Part 4: Delete Row Data
    if (isset($_POST['idDelete'])) {
        $id = $_POST['idDelete'];
        $conn = new mysqli($servername, $username, $password, $database);
        if ($conn->connect_error) {
            die("Connection failed: " . mysqli_connect_error());
        } 

        $query = "DELETE FROM eBook_MetaData WHERE id = ?";
        
        if ($stmt = $conn->prepare($query)) {
                $stmt->bind_param("i", $id);
                $result = $stmt->execute();
                $stmt->close();
                if ($result == true) { 
                    $_SESSION['msg'] = "Row id $id is Deleted";
                } else {
                    $_SESSION['msg'] = "The id $id For Deleting Does Not Exist";
                }
        } else {
            echo $conn->error;
        }

        $conn->close();
    }

    ?>

    <?php if (isset($_SESSION['msg'])) { ?>
        <div class = "msg">
            <?php 
            echo $_SESSION['msg']; 
            session_destroy();
            ?>
        </div>
    <?php } ?>

    <form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" > 
        <p>Create A New Book</p>
        
        <input type="hidden" name="id" value="<?php echo $id; ?>">
        <label>Creator</label>
        <input type="text" name="creator" value="<?php echo $creator; ?>"> <span class="error"><?php echo $creatorErr; ?></span>
        <label>Title</label>
        <input type="text" name="title" value="<?php echo $title; ?>"> <span class="error"><?php echo $titleErr; ?></span>
        <label>Type</label>
        <input type="text" name="type" value="<?php echo $type; ?>"> <span class="error"><?php echo $typeErr; ?></span>
        <label>Identifier</label>
        <input type="text" name="identifier" value="<?php echo $identifier; ?>"> <span class="error"><?php echo $identifierErr; ?></span>
        <label>Date</label>
        <input type="date" name="date" value="<?php echo $date; ?>"> <span class="error"><?php echo $dateErr; ?></span>
        <label>Language</label>
        <input type="text" name="language" value="<?php echo $language; ?>"> <span class="error"><?php echo $languageErr; ?></span>
        <label>Description</label>
        <textarea name="description" id="description" rows="5" cols="40"><?php echo $description; ?></textarea> <span class="error"><?php echo $descriptionErr; ?></span>
        
        <?php if ($edit_state == false) { ?>
            <button type="submit" name="create" id="submit">Create</button>
        <?php } else { ?>
            <button type="submit" name="update" id="submit">Update</button>
        <?php } ?>

    </form>
    
    



    <!-- Part 2: Retrieve Table Data Zone-->
    <h1>eBook_MetaData</h1>

    <table id="books">
        <tr>
            <th colspan="2">Action  </th>
            <th>id</th>
            <th>creator</th>
            <th>title</th>
            <th>type</th>
            <th>identifier</th>
            <th>date</th>
            <th>language</th>
            <th>description</th>
        </tr>

        <?php
            $conn = new mysqli($servername, $username, $password, $database);
            if ($conn->connect_error) {
                die("Connection failed: " . mysqli_connect_error());
            } 
    
            $sql = "SELECT `id`, `creator`, `title`, `type`, `identifier`, `date`, `language`, `description` FROM eBook_MetaData";
            $result = $conn->query($sql);
            
            if ($result->num_rows > 0) {
                while ($row = $result->fetch_assoc()){ ?>
                    <tr>
                        <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" method="post">
                            <td class="table-btn"><button type="submit" name="idEdit" value=<?php echo $row['id']; ?> class="edit-btn">Edit</td>
                            <td class="table-btn"><button type="submit" name="idDelete" value=<?php echo $row['id']; ?> class="delete-btn">Delete</td>
                            <td><?php echo $row['id']; ?></td>
                            <td><?php echo $row['creator']; ?></td>
                            <td><?php echo $row['title']; ?></td>
                            <td><?php echo $row['type']; ?></td>
                            <td><?php echo $row['identifier']; ?></td>
                            <td><?php echo $row['date']; ?></td>
                            <td><?php echo $row['language']; ?></td>
                            <td><?php echo $row['description']; ?></td>
                        </form>
                    </tr>


                <?php }
            }
            $conn->close();
        ?>

    </table>
</body>
</html>