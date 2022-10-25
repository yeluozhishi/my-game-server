import getters from "@/store/getters";
import user from "@/store/modules/user";
import Vuex from 'vuex'

// const store = createStore({
//     modules: {
//         user
//     },
//     getters,
// })

// Create a new store instance.


// eslint-disable-next-line no-undef
const store = new Vuex.Store({
    modules: {
        user
    },
    getters
})


export default store