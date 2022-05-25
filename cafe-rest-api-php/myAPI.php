<?php
    //MyAPI.php
    //selama berhubungan dengan data pada tabel, selalu dipanggil
    //file koneksi 
    require_once "koneksi.php";
    if ($_GET['menu']) {  //jika paramaeter function dikirim
        $_GET['menu'](); //jalankan parameter
    }

    function addMenu()
    {

        $upload_path = 'upload/';
        $server_ip = gethostbyname(gethostname());
        $upload_url = 'http://' . $server_ip . '/cafe/' . $upload_path;

        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            $nama = $_POST['nama'];
            $desk = $_POST['deskripsi'];
            $harga = $_POST['harga'];

            $imageNama = $_FILES['image']['name'];
            $imageSize = $_FILES['image']['size'];

            if (empty($nama) || empty($desk) || empty($harga) || empty($imageNama)) {
                $msg = json_encode(array("message" => "tidak boleh kosong", "status" => false));
                header('Content-Type: application/json');
                echo $msg;
            } else {
                global $koneksi;
                $fileInfo = pathinfo($_FILES['image']['name']);
                $fileUrl = $upload_url . 'image_' . $imageNama;
                $filePath = $upload_path . 'image_' . $imageNama;

                //mengambil extension file
                $fileExt = strtolower(pathinfo($imageNama, PATHINFO_EXTENSION));
                $valid_extension = array('jpg', 'jpeg', 'png', 'gif', 'jpe');

                //jika file gambar sesuai format 
                if (in_array($fileExt, $valid_extension)) {
                    if (!file_exists($filePath)) {
                        if ($imageSize > 2000000) {
                            $msg = json_encode(array("message" => "file terlalu besar", "status" => false));
                            header('Content-Type: application/json');
                            echo $msg;
                        }
                    } else {
                        $msg = json_encode(array("message" => "file sudah ada", "status" => false));
                        header('Content-Type: application/json');
                        echo $msg;
                    }
                } else {
                    $msg = json_encode(array("message" => "hanya jpeg, jpg, png dan gif yang di perbolehkan", "status" => false));
                    header('Content-Type: application/json');
                    echo $msg;
                }
            }
            if (!isset($msg)) {
                move_uploaded_file($_FILES['image']['tmp_name'], $filePath);

                $query = $koneksi->query("INSERT INTO menu (nama, deskripsi, harga, image)
                                    VALUES('$nama','$desk','$harga','$fileUrl');");

                if ($query) {
                    $msg = json_encode(array("message" => "berhasil menambahkan menu", "status" => true));
                    header('Content-Type: application/json');
                    echo $msg;
                } else {
                    $msg = json_encode(array("message" => "gagal menambahkan menu", "status" => false));
                    header('Content-Type: application/json');
                    echo $msg;
                }
            } else {
                $msg = json_encode(array("message" => "request tidak bisa di akses!", "status" => false));
                header('Content-Type: application/json');
                echo $msg;
            }
        }
    }

    function getAllMenu()
    {
        global $koneksi;

        $result = array();
        $query = $koneksi->query("SELECT * FROM menu order by id;");

        while ($row = mysqli_fetch_assoc($query)) {
            array_push($result, array(
                "id" => $row['id'],
                "nama" => $row['nama'],
                "deskripsi" => $row['deskripsi'],
                "harga" => $row['harga'],
                "image" => $row['image']
            ));
        }
        header('Content-Type: application/json');
        echo json_encode(array('result' => $result));
    }

    function addCart()
    {
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {

            $meja = $_POST['nomor_meja'];
            $menuId = $_POST['menu_id'];
            $jmlOrder = $_POST['jumlah_order'];

            if (empty($meja) || empty($menuId) || empty($jmlOrder)) {
                $msg = json_encode(array("message" => "kolom tidak boleh kosong", "status" => false));
                header('Content-Type: application/json');
                echo $msg;
            } else {
                global $koneksi;
                $query = $koneksi->query("INSERT INTO orders (nomor_meja, menu_id, jumlah_order)
                                                        VALUES ('$meja','$menuId','$jmlOrder')");
                if ($query) {
                    $msg = json_encode(array("message" => "Order Success", "status" => true));
                    header('Content_type: application/json');
                    echo $msg;
                } else {
                    $msg = json_encode(array("message" => "Order Failed", "status" => false));
                    header('Content_type: application/json');
                }
            }
        } else {
            $msg = json_encode(array("message" => "Access Denied", "status" => false));
            header('Content_type: application/json');
            echo $msg;
        }
    }
    
    function getAllCart()
    {
        global $koneksi;

        $result = array();
        $query = $koneksi->query("SELECT * FROM view_cart");

        while ($row = mysqli_fetch_assoc($query)) {
            array_push($result, array(
                "nomor_meja" => $row['nomor_meja'],
                "nama" => $row['nama'],
                "harga" => $row['harga'],
                "jumlah_order" => $row['jumlah_order'],
                "jumlah_harga" => $row['jumlah_harga']
            ));
        }
        header('Content-Type: application/json');
        echo json_encode(array('result' => $result));
    }

?>
