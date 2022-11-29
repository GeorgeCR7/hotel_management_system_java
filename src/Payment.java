public class Payment {

    private int id;
    private int bookingID;
    private int method; // 0 --> cash, 1 --> credit card.
    private int finalAmount;
    private int paymentDay;
    private int paymentMonth;
    private int paymentYear;

    public int computeFinalAmount(HotelDataBase db, int days, int roomID){

        int amount1 = 0, amount2 = 0, finalAmount = 0;
        int roomPrice = 0;
        int bookingSeason = 0;

        for(int i = 0 ; i < db.getRooms().size() ; i++){
            if(db.getRooms().get(i).getId() == roomID){
                roomPrice = db.getRooms().get(i).getRoomPrice();
            }
        }

        amount1 = days * roomPrice;

        for(int i = 0 ; i < db.getBookings().size() ; i++){
            if(db.getBookings().get(i).getRoomID() == roomID){
                bookingSeason = db.getBookings().get(i).getBookingSeason();
            }
        }

        if(bookingSeason == 0){
            amount2 = days * 1;
        }else if(bookingSeason == 1){
            amount2 = days * 2;
        }else if(bookingSeason == 2){
            amount2 = days * 3;
        }

        finalAmount = amount1 + amount2;
        return finalAmount;
    }

    public void showFinancialData(HotelDataBase db, int year){

        int totalYearAmount20 = 0, totalYearAmount21 = 0;

        int totalOctober20 = 0 , totalNovember20 = 0 , totalDecember20 = 0;
        int totalJanuary21 = 0, totalFebruary21 = 0, totalMarch21 = 0, totalApril21 = 0 ;

        for (int i = 0 ; i < db.getPayments().size() ; i++){
            if(db.getPayments().get(i).getPaymentYear() == 2020){
                totalYearAmount20 += db.getPayments().get(i).getFinalAmount();
                if(db.getPayments().get(i).getPaymentMonth() == 10){
                    totalOctober20 += db.getPayments().get(i).getFinalAmount();
                }else if(db.getPayments().get(i).getPaymentMonth() == 11){
                    totalNovember20 += db.getPayments().get(i).getFinalAmount();
                }else if(db.getPayments().get(i).getPaymentMonth() == 12){
                    totalDecember20 += db.getPayments().get(i).getFinalAmount();
                }
            }else if(db.getPayments().get(i).getPaymentYear() == 2021){
                totalYearAmount21 += db.getPayments().get(i).getFinalAmount();
                if(db.getPayments().get(i).getPaymentMonth() == 1){
                    totalJanuary21 += db.getPayments().get(i).getFinalAmount();
                }else if (db.getPayments().get(i).getPaymentMonth() == 2){
                    totalFebruary21 += db.getPayments().get(i).getFinalAmount();
                }else if (db.getPayments().get(i).getPaymentMonth() == 3){
                    totalMarch21 += db.getPayments().get(i).getFinalAmount();
                }else if (db.getPayments().get(i).getPaymentMonth() == 4){
                    totalApril21 += db.getPayments().get(i).getFinalAmount();
                }
            }
        }

        if(year == 2020){
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("|         YEAR 2020 FINANCIAL DATA      |");
            System.out.println("-----------------------------------------");
            System.out.println();
            System.out.println("Total amount for " + year + ": " + totalYearAmount20 + " €");
            System.out.println("October's " + year + " total amount: " + totalOctober20 + " €");
            System.out.println("November's " + year + " total amount: " + totalNovember20 + " €");
            System.out.println("December's " + year + " total amount: " + totalDecember20 + " €");
        }else{
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("|         YEAR 2021 FINANCIAL DATA      |");
            System.out.println("-----------------------------------------");
            System.out.println();
            System.out.println("Total amount for " + year + ": " + totalYearAmount21 + " €");
            System.out.println("January's " + year + " total amount: " + totalJanuary21 + " €");
            System.out.println("February's " + year + " total amount: " + totalFebruary21 + " €");
            System.out.println("March's " + year + " total amount: " + totalMarch21 + " €");
            System.out.println("April's " + year + " total amount: " + totalApril21 + " €");
        }
    }

    public void showStatsData(HotelDataBase db, int year){

        int paid20Bookings = 0, paid21Bookings = 0;
        int active20Bookings = 0, active21Bookings = 0;

        //Count all the paid bookings for each year.
        for (Payment payment : db.getPayments()){
            if (payment.getPaymentYear() == 2020){
                paid20Bookings++;
            }else{
                paid21Bookings++;
            }
        }

        //Count all the active booking for each year.
        for (Booking booking: db.getBookings()){
            if (booking.getBookingYear() == 2020){
                active20Bookings++;
            }else{
                active21Bookings++;
            }
        }


        System.out.println();
        if (year == 2020){
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("|            YEAR 2020 STATS            |");
            System.out.println("-----------------------------------------");
            System.out.println();
            System.out.println("Total paid bookings in 2020: " + paid20Bookings);
            System.out.println("Total active bookings in 2020: " + active20Bookings);
        }else{
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("|            YEAR 2021 STATS            |");
            System.out.println("-----------------------------------------");
            System.out.println();
            System.out.println("Total paid bookings in 2021: " + paid21Bookings);
            System.out.println("Total active bookings in 2021: " + active21Bookings);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(int finalAmount) {
        this.finalAmount = finalAmount;
    }

    public int getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(int paymentDay) {
        this.paymentDay = paymentDay;
    }

    public int getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(int paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    public int getPaymentYear() {
        return paymentYear;
    }

    public void setPaymentYear(int paymentYear) {
        this.paymentYear = paymentYear;
    }
}