import axios from 'axios'

export const fetchCustomer = async (customer_id) => {
  const { data } = await axios.get(
      `${process.env.REACT_APP_BASE_ENDPOINT}/api/customer/get?id=${customer_id}`
     
  );
  return data;
}

export const fetchCreditApplicationAdd =  (identity_number) => {
  return axios.post(
      `${process.env.REACT_APP_BASE_ENDPOINT}/api/credit_application/add`,identity_number
  );
}

export const fetchCustomerAdd =  (customer) => {
 return  axios.post(
      `${process.env.REACT_APP_BASE_ENDPOINT}/api/customer/add`,customer
  )
}

export const fetchCreditApplicationGet =  (identity_number) => {
 return axios.get(
      `${process.env.REACT_APP_BASE_ENDPOINT}/api/credit_application/get_credit_application?identityNumber=${identity_number}`

  );
}