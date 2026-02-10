document.getElementById('getDoctorListForm').addEventListener('submit', async function(event) {
    event.preventDefault();
    const patientId = document.getElementById('patientId').value;

    try {
        const response = await fetch(`http://localhost:8080/api/v1/patient/getDoctorList/${patientId}`);
        const data = await response.json();

        const responseElement = document.getElementById('response');
        const doctorListTable = document.getElementById('doctorListTable').getElementsByTagName('tbody')[0];

        doctorListTable.innerHTML = '';

        if (data.code === "08") {
            responseElement.className = 'response success';
            responseElement.textContent = data.message;

            data.data.forEach(doctor => {
                const row = doctorListTable.insertRow();
                const cell1 = row.insertCell(0);
                const cell2 = row.insertCell(1);
                const cell3 = row.insertCell(2);

                cell1.textContent = doctor.id;
                cell2.textContent = doctor.firstName;
                cell3.textContent = doctor.phone;
            });
        } else {
            responseElement.className = 'response error';
            responseElement.textContent = data.message;
        }

        responseElement.style.display = 'block';

    } catch (error) {
        console.error('Error:', error);
        const responseElement = document.getElementById('response');
        responseElement.className = 'response error';
        responseElement.textContent = 'An error occurred while fetching the doctor list.';
        responseElement.style.display = 'block';
    }
});
