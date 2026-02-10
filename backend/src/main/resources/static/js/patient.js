cleanForm();

function cleanForm() {
    document.getElementById("patientForm").reset();
}


function savePatient() {
    let email = $('#emailInsert').val();
    let firstName = $('#InputFirstName').val();
    let lastName = $('#InputLastName').val();
    let dob = $('#InputDOB').val();
    let address = $('#InputAddress').val();
    let number = $('#InputNumber').val();
    let gender = $('#InputGender').val();
    let doctorsIDs = $('#InputDoctorID').val(); // Convert to list of integers
    let attendant = $('#InputAttendantID').val();
    let nurse = $('#InputNurseID').val();

    // Constructing NurseDto and AttendantDto as objects
    let nurseDto;
    if (nurse) {
        nurseDto = {
            "id": parseInt(nurse)
        };
    } else {
        nurseDto = null;
    }

    let attendantDto;
    if (attendant) {
        attendantDto = {
            "id": parseInt(attendant),
        };
    } else {
        attendantDto = null;
    }

    let doctorDto;
    if (doctorsIDs) {
        doctorDto = [parseInt(doctorsIDs)]
    } else {
        doctorDto = null;
    }

    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/v1/patient/savePatient",
        async: true,
        data: JSON.stringify({
            "id": "",
            "email": email,
            "firstName": firstName,
            "lastName": lastName,
            "dob": dob,
            "address": address,
            "phone": number,
            "gender": gender === "Open this select menu" ? null : gender, // Handle placeholder value
            "nurse": nurseDto,
            "attendant": attendantDto,
            "doctorsIDs": doctorDto
        }),
        success: function (data, textStatus, xhr) {
            if (xhr.status === 202) {
                alert(data.code + " : " + data.message);
                console.log(data.data);
                cleanForm();
            } else {
                alert("Unexpected response: " + data.code + " : " + data.message);
            }
        },
        error: function (xhr, exception) {
            alert("Error: " + xhr.responseJSON.code + " : " + xhr.responseJSON.message);
        }
    });
}


document.addEventListener('DOMContentLoaded', () => {
    fetch('http://localhost:8080/api/v1/doctor/getAllDoctors')
        .then(response => response.json())
        .then(data => {
            populateDropdown(data.data, "InputDoctorID");
        })
        .catch(error => console.error('Error fetching data:', error));

    fetch('http://localhost:8080/api/v1/nurse/getAllNurses')
        .then(response => response.json())
        .then(data => {
            populateDropdown(data.data, "InputNurseID");
        })
        .catch(error => console.error('Error fetching data:', error));

    fetch('http://localhost:8080/api/v1/attendant/getAllAttendants')
        .then(response => response.json())
        .then(data => {
            populateDropdown(data.data, "InputAttendantID");
        })
        .catch(error => console.error('Error fetching data:', error));
});

function populateDropdown(peoples, id) {
    const dropdown = document.getElementById(id);
    try {
        peoples.forEach(person => {
            const option = document.createElement('option');
            option.value = person.id;
            option.textContent = person.firstName; // Assuming doctor has a firstName property
            dropdown.appendChild(option);

        })
    } catch (error) {
        console.error('Error populating dropdown:', error);
    }
}




document.getElementById('registerButton').addEventListener('click', function() {
    const button = this;
    button.classList.add('clicked');

    // Remove the class after the animation ends to allow re-clicking
    setTimeout(function() {
        button.classList.remove('clicked');
    }, 500); // Duration should match the CSS animation duration
});

