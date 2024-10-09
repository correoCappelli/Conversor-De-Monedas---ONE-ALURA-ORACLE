public record apiEXCHANGE(
        String base_code,
		String target_code,
		Double conversion_rate,
		Double conversion_result,
		String result


) {
	@Override
	public String toString() {
		return "  " + base_code + "  equivalen a " + conversion_result + " " +target_code +  "  utilizando una tase de cambio de  " + conversion_rate;
	}
}
