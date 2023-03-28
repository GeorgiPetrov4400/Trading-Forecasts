let forecastContainer = document.getElementById('forecast-container')
forecastContainer.innerHTML = ''

fetch(`http://localhost:8080/api/free-forecasts`)
    .then(response => response.json())
    .then(json => json.forEach(forecast => {

        let row = document.createElement('tr')

        let categoryCol = document.createElement('td')
        let typeCol = document.createElement('td')
        let pictureCol = document.createElement('td')
        let descriptionCol = document.createElement('td')

        categoryCol.textContent = forecast.category
        typeCol.textContent = forecast.type
        pictureCol.textContent = forecast.picture
        descriptionCol.textContent = forecast.description

        row.appendChild(categoryCol)
        row.appendChild(typeCol)
        row.appendChild(pictureCol)
        row.appendChild(descriptionCol)

        forecastContainer.appendChild(row)
    }))
